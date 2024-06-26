package com.cohotz.survey.service.impl;

import com.cohotz.survey.client.api.UserService;
import com.cohotz.survey.client.core.model.CultureBlockMin;
import com.cohotz.survey.client.profile.model.UserCohort;
import com.cohotz.survey.client.profile.model.UserRes;
import com.cohotz.survey.config.SurveyConfiguration;
import com.cohotz.survey.dao.ParticipantDao;
import com.cohotz.survey.dao.SurveyDao;
import com.cohotz.survey.dto.email.ParticipantNotificationDetails;
import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.dto.response.ParticipantMinRes;
import com.cohotz.survey.dto.response.participant.AssignedSurvey;
import com.cohotz.survey.manager.QuestionManager;
import com.cohotz.survey.model.Cohort;
import com.cohotz.survey.model.Participant;
import com.cohotz.survey.model.Survey;
import com.cohotz.survey.model.SurveyStatus;
import com.cohotz.survey.model.engine.WeightedEngineScore;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.model.response.Response;
import com.cohotz.survey.model.survey.CohortItem;
import com.cohotz.survey.service.MailService;
import com.cohotz.survey.service.NotificationService;
import com.cohotz.survey.service.SurveyParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.cohotz.boot.error.CHException;
import org.cohotz.boot.model.common.CohortProcessor;
import org.cohotz.boot.model.common.CohotzEntity;
import org.cohotz.boot.utils.FieldUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.cohotz.survey.error.ServiceCHError.*;

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
    NotificationService notificationService;

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
        String userName = user.getFirstName() != null ? user.getFirstName() : "John" + " " +user.getLastName() != null ? user.getLastName() : "Doe";
        List<String> reportingHierarchy = userService.fetchReportingHierarchy(survey.getTenant(), email);
        Participant participant = new Participant(email, userName, user.getReportingTo(), survey.getId(), survey.getName(), user.getTenant(), survey.getEndDate(), user.getCommunicationSettings());
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
        participant.setCohorts(fetchCohorts(user, survey.getCohorts()));

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
    public void emailParticipantForSurvey(Survey survey) {
        List<ParticipantNotificationDetails> participants = new ArrayList<>();
        dao.findByTenantAndSurveyId(survey.getTenant(), survey.getId())
                .forEach(p -> {
                    participants.add(new ParticipantNotificationDetails(p.getName(), p.getEmail(), p.getCommunicationSettings(), String.format(
                            config.getSurveyLinkFormat(),
                            survey.getTenant(),
                            survey.getId(),
                            p.getAccessCode())));
                    p.setSurveyStatus(SurveyStatus.STARTED);
                    dao.save(p);
                });
        switch (survey.getType()) {
            case ENGAGEMENT:
                mailService.sendEngagementSurvey(survey, participants);
                break;
            default:
                participants.forEach(p -> notificationService.sendSurvey(survey, p));
                break;
        }

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
    public List<Participant> findAllByEmail(String tenant, String email) {
        return dao.findByTenantAndEmail(tenant, email);
    }

    @Override
    public List<AssignedSurvey> findAssignedSurveys(String tenant, String email, boolean pending) {
        return findAllByEmail(tenant, email)
                .stream()
                .filter(p -> pending ? (!p.isSurveyComplete() && p.getDueDate().isAfter(LocalDateTime.now(ZoneOffset.UTC))) : true)
                .filter(p -> p.getSurveyStatus().inProgress())
                .map(p -> AssignedSurvey.builder().surveyId(p.getSurveyId()).surveyName(p.getSurveyName())
                        .accessCode(p.getAccessCode())
                        .lastReminder(p.getLastReminder())
                        .reminderFrequencyInDays(p.getReminderFrequencyInDays())
                        .cohortParticipation(dao.cohortCompletion(tenant, p.getSurveyId()))
                        .link(String.format(String.format(config.getSurveyLinkFormat(), tenant, p.getSurveyId(), p.getAccessCode())))
                        .dueDate(p.getDueDate()).complete(false).build()).collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    private List<Cohort> fetchCohorts(UserRes u, List<CohortItem> cohortItems) {
        //log.info("Fetching Cohorts for [{}] : [{}]", u.getEmail(), u.getCohorts());
        //log.info("Survey level Cohort Mapper: [{}]", cohortItems);
        List<Cohort> cohorts = new ArrayList<>();
        for(CohortItem ci : cohortItems) {
            //log.info("Processing Cohort Item: [{}]", ci);
            for(UserCohort uci : u.getCohorts()) {
                //log.info("Processing User Cohort Item: [{}]", uci);
                if(uci.getName().equals(ci.getField())) {
                    //log.info("Processing Cohort [{}] : [{}]", uci.getKey(), uci.getValue());
                    if(ci.getProcessor().equals(CohortProcessor.NONE)){
                        cohorts.add(new Cohort(ci.getDisplayName(), (String) uci.getValue()));
                    } else {
                        cohorts.add(new Cohort(ci.getDisplayName(), cohortProcessor(ci, uci)));
                    }
                }
            }
        }
        return cohorts;
    }

    private String cohortProcessor(CohortItem ci, UserCohort uci) {
        Class<FieldUtils> clazz = FieldUtils.class;
        Method method = null;
        try {
            method = clazz.getMethod(ci.getProcessor().getMethod(), Object.class);
            Object result = method.invoke(null, uci.getValue());
            return (String) result;
        } catch (NoSuchMethodException e) {
            log.error("Cohort Processor Method NoSuchMethodException [{}] not found. Skipping Cohort [{}] : [{}]", ci.getProcessor().getMethod(), uci.getName(), uci.getValue());
        } catch (InvocationTargetException e) {
            log.error("Cohort Processor Method InvocationTargetException [{}] . Skipping Cohort [{}] : [{}] : [{}]", ci.getProcessor().getMethod(), uci.getName(), uci.getValue(), e.getMessage());
        } catch (IllegalAccessException e) {
            log.error("Cohort Processor Method IllegalAccessException [{}] . Skipping Cohort [{}] : [{}]: [{}]" , ci.getProcessor().getMethod(), uci.getName(), uci.getValue(), e.getMessage());
        }
        return null;
    }
}
