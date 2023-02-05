package com.cohotz.survey.service.impl;

import com.cohotz.survey.client.api.UserService;
import com.cohotz.survey.client.core.model.CultureBlockMin;
import com.cohotz.survey.client.profile.model.UserRes;
import com.cohotz.survey.config.SurveyConfiguration;
import com.cohotz.survey.dao.ParticipantDao;
import com.cohotz.survey.dao.SurveyDao;
import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.dto.response.ParticipantMinRes;
import com.cohotz.survey.manager.QuestionManager;
import com.cohotz.survey.model.Cohort;
import com.cohotz.survey.model.Participant;
import com.cohotz.survey.model.Survey;
import com.cohotz.survey.model.SurveyStatus;
import com.cohotz.survey.model.engine.WeightedEngineScore;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.model.response.Response;
import com.cohotz.survey.service.MailService;
import com.cohotz.survey.service.SurveyParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.cohotz.boot.error.CHException;
import org.cohotz.boot.model.common.CohotzEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.cohotz.survey.error.ServiceCHError.*;
import static com.cohotz.survey.utils.ProfileUtils.getYearRange;

@Service
@Slf4j
public class SurveyParticipantServiceImpl implements SurveyParticipantService {

    @Autowired
    ParticipantDao dao;

    @Autowired
    SurveyDao surveyDao;

    @Autowired
    SurveyConfiguration config;

    @Autowired
    MailService mailService;

    @Autowired
    UserService userService;

    @Autowired
    ApplicationContext context;

    @Override
    public void createParticipants(Survey survey) throws CHException {
        Set<String> participantEmails = CollectionUtils
                .emptyIfNull(userService.usersByReporting(survey.getTenant(), survey.getPublisher()))
                .stream()
                .map(UserRes::getEmail)
                .collect(Collectors.toSet());
        createParticipants(survey, participantEmails);
    }

    @Override
    public void createParticipants(Survey survey, Set<String> participants) {
        participants.forEach(email -> {
            try {
                createParticipant(email, survey);
            }catch (CHException e) {
                log.error("Error while creating participant. Skipping participant: [{}] [{}]",
                        e.getError().getCode(), e.getError().getDescription());
            }
        });
    }

    @Override
    @Async
    public Participant createParticipant(String email, Survey survey) throws CHException {
        log.debug("Creating participant {} for survey {}", email, survey.getId());
        Map<String, Response> smartSkipEligibleQuestions = fetchSmartSkipEligibleQuestions(survey.getTenant(), email);
        UserRes user = userService.fetchByTenantAndEmail(survey.getTenant(), email);
        List<String> reportingHierarchy = userService.fetchReportingHierarchy(survey.getTenant(), email);
        Participant participant = new Participant(email, user.getName(), user.getReportingTo(), survey.getId(), survey.getName(), user.getTenant(), survey.getEndDate());
        participant.setReportingHierarchy(reportingHierarchy);
        survey.getQuestionMap().forEach((qCode, question) -> {
            QuestionManager rm = (QuestionManager)context.getBean(question.getResponseType());
            StaticSurveyQuestion sQuestion = rm.copySurveyQuestion(question);
            if(smartSkipEligibleQuestions.get(sQuestion.getPoolQuesReferenceCode()) != null) {
                sQuestion.setSmartSkip(true);
            }
            participant.getQuestionMap().put(qCode, sQuestion);
        });
        survey.getQuestionMap().forEach((qCode, q) -> {
            Response response = new Response();
            Response recentResponse = smartSkipEligibleQuestions.get(qCode);
            if (recentResponse != null && survey.isSmartSkip()) {
                log.debug("Recent response for question {} found for participant {}. Auto updating response", qCode, user.getEmail());
                BeanUtils.copyProperties(recentResponse, response);
                response.setSmartSkip(true);
            }
            participant.getResponseMap().put(q.getPoolQuesReferenceCode(), response);
        });

        //Create blank engines for participant
        survey.getEngines().forEach(e -> {
            participant.getEngineScore()
                    .put(e.getCode(), new WeightedEngineScore(e.getName(), e.getCode(), e.getWeight(), 0d, e.getQuestionCount(), 0d));
            participant.getEngines().add(new CohotzEntity(e.getName(), e.getCode()));
        });

        participant.setSurveyStatus(SurveyStatus.DRAFT);
        participant.setCohorts(fetchCohorts(user));

        participant.setBlock(new CultureBlockMin().code(survey.getBlock().getCode()).name(survey.getBlock().getName()));
        return dao.save(participant);
    }

    private Map<String, Response> fetchSmartSkipEligibleQuestions(String tenant, String email) {
        Map<String, Response> smartSkipEligibleQuestions = new HashMap<>();
        List<Participant> participation = findAllForEmployee(
                tenant,
                email,
                LocalDateTime.now(ZoneOffset.UTC).minusMonths(config.getSmartThreshold()));

        participation.forEach(p -> smartSkipEligibleQuestions.putAll(p.getResponseMap()));
        return smartSkipEligibleQuestions;
    }

    @Override
    public Participant updateParticipant(Participant participant) {
        log.debug("Updating participant {}", participant);
        return dao.save(participant);
    }

    @Override
    public void updateScoreAccFlag(String tenant, String surveyId, String participantId) {
        dao.findByTenantAndSurveyIdAndId(tenant, surveyId, participantId)
                .ifPresent(p -> {
                    p.setScoreAccumulated(true);
                    dao.save(p);
                });
    }

    @Override
    public List<Participant> findAllParticipantForSurvey(String tenant, String surveyId) {
        return dao.findByTenantAndSurveyId(tenant, surveyId);
    }

    @Override
    public List<Participant> findAllParticipantWithNewResponse(String tenant, String surveyId) {
        return dao.findByTenantAndSurveyIdAndSurveyCompleteAndScoreAccumulated(tenant, surveyId, true, false);
    }

    @Override
    public long surveyParticipantCount(String tenant, String surveyId) {
        return dao.countByTenantAndSurveyId(tenant, surveyId);
    }

    @Override
    public long surveyResponseCount(String tenant, String surveyId) {
        return dao.countByTenantAndSurveyIdAndSurveyComplete(tenant, surveyId, true);
    }

    @Override
    @Async
    public void emailParticipantForSurvey(String tenant, String surveyId, Survey survey) {
        dao.findByTenantAndSurveyId(tenant, surveyId)
                .forEach(p -> {
                    String email = survey.getType().equals(Survey.SurveyType.ENGAGEMENT) ? survey.getPublisher() : p.getEmail();
                    mailService.sendSurvey(survey, email, String.format(
                            config.getSurveyLinkFormat(),
                            tenant,
                            surveyId,
                            p.getAccessCode()));
                    p.setSurveyStatus(SurveyStatus.STARTED);
                    dao.save(p);
                });
    }

    @Override
    public void emailManagerForSurvey(String tenant, String surveyId, Survey survey) {
        dao.findByTenantAndSurveyId(tenant, surveyId)
                .forEach(p -> {
                    String email = survey.getType().equals(Survey.SurveyType.ENGAGEMENT) ? survey.getPublisher() : p.getEmail();
                    mailService.sendEngagementSurvey(survey, email, String.format(
                            config.getSurveyLinkFormat(),
                            tenant,
                            surveyId,
                            p.getAccessCode()));
                    p.setSurveyStatus(SurveyStatus.STARTED);
                    dao.save(p);
                });
    }

    @Override
    @Async
    public void updateStatus(String tenant, String surveyId, SurveyStatus status) {
        dao.findByTenantAndSurveyId(tenant, surveyId).forEach(p -> {
            log.debug("Updating status for participant: [{}] from [{}] to [{}]", p.getEmail(), p.getSurveyStatus(), status);
            p.setSurveyStatus(status);
            dao.save(p);
        });
    }

    @Override
    public List<Participant> findAllForEmployee(String tenant, String email, LocalDateTime responseTS) {
        return dao.findByTenantAndEmailAndResponseTSGreaterThanEqual(tenant, email, responseTS);
    }

    @Override
    public Participant findParticipantByEmailForSurvey(String surveyId, String tenant, String email) {
        log.debug("Finding Participant {} for survey {} and tenant {}", email, surveyId, tenant);
        return dao.findBySurveyIdAndTenantAndEmail(surveyId, tenant, email).orElse(null);
    }

    @Override
    public Participant findParticipantByAccessCodeForSurvey(String surveyId, String tenant, String accessCode) {
        log.debug("Finding Participant {} for survey {} and tenant {}", accessCode, surveyId, tenant);
        return dao.findBySurveyIdAndTenantAndAccessCode(surveyId, tenant, accessCode).orElse(null);
    }

    @Override
    public void updateReminder(String tenant, String surveyId, String accessCode) {
        dao.findBySurveyIdAndTenantAndAccessCode(surveyId, tenant, accessCode)
                .ifPresent(p -> {
                    p.setLastReminder(LocalDateTime.now(ZoneOffset.UTC));
                    dao.save(p);
                });
    }

    @Override
    public void addResponse(String tenant, String surveyId, String accessCode, List<ResponseDTO> responses) throws CHException {

        Participant participant = findParticipantByAccessCodeForSurvey(surveyId, tenant, accessCode);

        //Validate the participant and submitted response
        responseValidation(responses, participant);

        Map<String, WeightedEngineScore> participantScore = new HashMap<>();
        log.info("Capturing response for participant {} for survey {} and tenant {}", participant.getEmail(), surveyId, tenant);

        for(ResponseDTO r : responses) {
            StaticSurveyQuestion ques = participant.getQuestionMap().get(r.getQuestionCode());
            QuestionManager rm = (QuestionManager)context.getBean(ques.getResponseType());
            rm.validate(r);
            Response response = rm.evaluate(r, ques, participant);
            participant.getResponseMap().put(r.getQuestionCode(), response);

            //Update score
            log.debug("Available engines: {} to be matched with {}", participant.getEngines(), ques.getEngine().getCode());

            //Aggregate the engine score
            WeightedEngineScore partEngineWeight = participantScore.get(ques.getEngine().getCode()) == null ?
                    new WeightedEngineScore() : participantScore.get(ques.getEngine().getCode());
            partEngineWeight.setCode(ques.getEngine().getCode());
            partEngineWeight.setName(ques.getEngine().getName());
            partEngineWeight.setWeight(participant.getEngineScore().values().stream().filter(e -> e.getCode().equals(ques.getEngine().getCode())).findFirst().orElseGet(() -> new WeightedEngineScore()).getWeight());
            partEngineWeight.setScore(partEngineWeight.getScore() + response.getScore());
            partEngineWeight.setQuestionCount(partEngineWeight.getQuestionCount() + 1);
            participantScore.put(ques.getEngine().getCode(), partEngineWeight);
            participant.getEngineScore().put(ques.getEngine().getCode(), partEngineWeight);
        }

        //Update the survey level engine score with participant's response evaluation
        participant.getEngineScore().forEach((ec, ew) -> {
            ew.setScore(ew.getScore() + participant.getEngineScore().get(ew.getCode()).getScore());
            ew.setQuestionCount(ew.getQuestionCount() + participant.getEngineScore().get(ew.getCode()).getQuestionCount());
        });

        participant.setSurveyComplete(true);
        participant.setResponseTS(LocalDateTime.now(ZoneOffset.UTC));
        updateParticipant(participant);
    }

    private void responseValidation(List<ResponseDTO> responses, Participant participant) throws CHException {
        log.debug("Validating Survey response for participant: {}", participant.getEmail());
        if (participant == null) throw new CHException(CULTR_SURVEY_PARTICIPANT_NOT_FOUND);
        if (participant.isSurveyComplete()) throw new CHException(CULTR_SURVEY_ALREADY_SUBMITTED);
        if (participant.getSurveyStatus().surveyClosed()) {
            throw new CHException(CULTR_SURVEY_CLOSED);
        }else if(participant.getSurveyStatus().surveyNotStarted()){
            throw new CHException(CULTR_SURVEY_NOT_STARTED);
        }

        //Validate for duplicate responses
        if(responses.size() != Set.copyOf(responses).size()) throw new CHException(CULTR_SURVEY_DUPLICATE_ANSWERS);

        //Validate that all mandatory/non-smart skipped questions are answered
        AtomicBoolean missingResponse = new AtomicBoolean(false);
        AtomicBoolean invalidSelection = new AtomicBoolean(false);
        participant.getResponseMap().forEach((q,r) -> {
            QuestionManager qm = (QuestionManager) context.getBean(participant.getQuestionMap().get(q).getResponseType());
            ResponseDTO responseDto = responses.stream().filter(res -> res.getQuestionCode().equals(q)).findFirst().orElse(null);
            invalidSelection.set(qm.validateResponse(responseDto, participant.getQuestionMap().get(q)));
        });
        if(missingResponse.get()) throw new CHException(CULTR_SURVEY_MISSING_MANDATORY_RESPONSE);
        if(invalidSelection.get()) throw new CHException(CULTR_SURVEY_RESPONSE_INVALID_OPTION);
    }

    @Override
    public List<ParticipantMinRes> listParticipant(String tenant, String id) {
        List<ParticipantMinRes> participants = new ArrayList<>();
        findAllParticipantForSurvey(tenant, id).forEach(p -> {
            participants.add(
                    new ParticipantMinRes(
                            p.isSurveyComplete(),
                            String.format(config.getSurveyLinkFormat(), p.getTenant(), p.getSurveyId(), p.getAccessCode()),
                            p.getEmail()));
        });

        return participants;
    }

    @Override
    public void deleteBySurveyId(String surveyId) {
        log.debug("Deleting all participants of survey {}", surveyId);
        dao.deleteBySurveyId(surveyId);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    private List<Cohort> fetchCohorts(UserRes u) {
        List<Cohort> cohorts = new ArrayList<>();
        config.getCohorts().forEach((k, v) -> {
            try {
                Field field = UserRes.class.getDeclaredField(k);
                field.setAccessible(true);
                if (List.of("currentExperience", "totalExperience").contains(k)) {
                    cohorts.add(new Cohort(v, getYearRange((Double) field.get(u))));
                } else {
                    cohorts.add(new Cohort(v, (String) field.get(u)));
                }
            } catch (Exception e) {
                log.error("Error while creating cohort: [{}]", e.getMessage());
            }
        });
        return cohorts;
    }
}
