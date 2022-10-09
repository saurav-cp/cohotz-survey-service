package com.cohotz.survey.service.impl;

import com.cohotz.survey.client.api.CultureBlockService;
import com.cohotz.survey.client.api.QuestionPoolService;
import com.cohotz.survey.client.api.UserService;
import com.cohotz.survey.client.core.model.*;
import com.cohotz.survey.client.profile.model.UserRes;
import com.cohotz.survey.config.SurveyConfiguration;
import com.cohotz.survey.dao.SurveyDao;
import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.dto.request.SurveyDTO;
import com.cohotz.survey.dto.response.QuestionMinRes;
import com.cohotz.survey.dto.response.*;
import com.cohotz.survey.esr.ESRRecordPublisher;
import com.cohotz.survey.manager.QuestionManager;
import com.cohotz.survey.model.Participant;
import com.cohotz.survey.model.Survey;
import com.cohotz.survey.model.SurveyStatus;
import com.cohotz.survey.esr.avro.BlockDetails;
import com.cohotz.survey.esr.avro.Cohort;
import com.cohotz.survey.esr.avro.EngineDetails;
import com.cohotz.survey.esr.avro.EngineScoreRecord;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.model.response.Response;
import com.cohotz.survey.service.MailService;
import com.cohotz.survey.service.SurveyParticipantService;
import com.cohotz.survey.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cohotz.boot.error.CHException;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.cohotz.survey.SurveyConstants.SURVEY_SERVICE_CLIENT;
import static com.cohotz.survey.error.ServiceCHError.*;
import static org.cohotz.boot.CHConstants.LOG_TRACE_ID;

@Service
@Slf4j
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    SurveyDao dao;

//    @Autowired
//    QuestionDao questionDao;

    @Autowired
    SurveyParticipantService participantService;

    @Autowired
    CultureBlockService blockService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionPoolService questionService;

    @Autowired
    SurveyConfiguration config;

    @Autowired
    MailService mailService;

//    @Autowired
//    UserDao userDao;

    @Autowired
    ESRRecordPublisher esrRecordPublisher;

    @Autowired
    ApplicationContext context;

    @Override
    public List<String> futureStatuses(String currentState) {
        return config.getStatusMap().get(currentState);
    }

    @Override
    public List<SurveyMinRes> fetchAll(String tenant, String status, String search) {
        if (StringUtils.isNotEmpty(status)) {
            if (StringUtils.isNotEmpty(search)) {
                log.debug("Tenant: {} - Both search: {} and status: {} provided", tenant, search, status);
                return dao.findByNameRegexAndTenantAndStatus(".*" + search + ".*", tenant, status);
            }
            log.debug("Tenant: {} - only status: {} provided", tenant, status);
            return dao.findByTenantAndStatus(tenant, status);
        } else {
            if (StringUtils.isNotEmpty(search)) {
                log.debug("Tenant: {} - only search: {} provided", tenant, search);
                return dao.findByNameRegexAndTenant(".*" + search + ".*", tenant);
            }
            log.debug("Tenant: {}", tenant);
            return dao.findByTenant(tenant);
        }

    }

    @Override
    public List<SurveyMinRes> fetchAllIncomplete(String tenant) {
        return dao.findByTenantAndStatusIn(tenant, List.of(SurveyStatus.PUBLISHED.name(), SurveyStatus.STARTED.name()));
    }

    @Override
    public List<Survey> fetchAllForPublisher(String tenant, String email) {
        return dao.findByTenantAndPublisher(tenant, email);
    }

    @Override
    @Async
    public void createSurvey(final SurveyDTO dto, String tenant, String publisher) throws CHException {
        createSurveySync(dto, tenant, publisher);
    }

    @Override
    public Survey createSurveySync(final SurveyDTO dto, String tenant, String pub) throws CHException {
        Survey survey = new Survey();
        BeanUtils.copyProperties(dto, survey);
        UserRes publisher = userService.fetchByTenantAndEmail(tenant, pub);
        CultureBlock block = blockService.fetchByCode(tenant, dto.getBlock());
        for(Question q : block.getQuestions()) {
            PoolQuestion poolQuestion = questionService.fetchByTenantAndCode(tenant, q.getPoolQuesReferenceCode());
            log.debug("Creating question {} for survey {} from block {} in position {}"
                    , poolQuestion.getCode(), survey.getName(), dto.getBlock(), q.getPosition());
            QuestionManager qm = (QuestionManager) context.getBean(poolQuestion.getResponseType());
            survey.getQuestionMap().put(poolQuestion.getCode(), qm.createSurveyQuestion(q, poolQuestion));
        }

        block.getEngines().forEach(e -> {
            EngineWeight ew = new EngineWeight();
            BeanUtils.copyProperties(e, ew);
            survey.getEngines().add(ew);
        });
        survey.setFormula(block.getFormula());
        survey.setStatus(SurveyStatus.DRAFT);
        survey.setLastReminder(LocalDateTime.now());
        survey.setTenant(tenant);
        survey.setBlock(new CultureBlockMin().name(block.getName()).code(block.getCode()));
        survey.setPartCount(CollectionUtils.emptyIfNull(dto.getSurveyParticipants()).size());
        survey.setCompletedDate(dto.getEndDate());
        survey.setPublisher(pub);
        survey.setPublisherName(publisher.getFirstName() +" "+ publisher.getLastName());

        log.debug("Creating survey: {}", survey);
        Survey s = dao.save(survey);

        log.info("Creating participants for survey {} for pub {}", s.getId(), s.getPublisher());
        participantService.createParticipantsSync(s.getId(), tenant, dto, block.getQuestions(), survey.getEngines());
        return s;
    }

    @Override
    public SurveyRes details(String tenant, String id) throws CHException {
        DecimalFormat df = new DecimalFormat("###.##");
        SurveyRes res = new SurveyRes();
        Survey survey = fetchSurveyByIdAndTenant(id, tenant);

        //survey.getEngines().forEach(e -> res.getEngineScore().put(e.getCode()));

        BeanUtils.copyProperties(survey, res);

        res.setBlock(survey.getBlock().getName());

        List<EngineWeight> engineWeights = new ArrayList<>();
        survey.getEngines().forEach(engine -> {
            EngineWeight engineWeight = new EngineWeight();
            BeanUtils.copyProperties(engine, engineWeight);
            if(engineWeight.getQuestionCount() == 0){
                engineWeight.setScore(0d);
            }else{
                engineWeight.setScore( Double.parseDouble(df.format(engineWeight.getScore() / engineWeight.getQuestionCount())));
            }
            engineWeights.add(engineWeight);
        });
        res.setEngines(engineWeights);

        participantService.findAllParticipantForSurvey(tenant, survey.getId()).forEach(p -> {
            ParticipantMinRes pRes = new ParticipantMinRes();
            pRes.setComplete(p.isSurveyComplete());
            pRes.setEmail(p.getEmail());
            pRes.setLink(String.format(config.getSurveyLinkFormat(), survey.getTenant(), survey.getId(), p.getAccessCode()));
            res.getParticipants().add(pRes);
        });

        survey.getQuestionMap().forEach((code, q) -> {
            QuestionMinRes qRes = new QuestionMinRes();
            BeanUtils.copyProperties(q, qRes);
            qRes.setEngine(q.getEngine().getName());
            qRes.setQuestion(q.getText());
            res.getQuestions().add(qRes);
        });
        return res;

    }

    @Override
    public List<EngineScoreRes> surveyEngineScore(String tenant, String id) throws CHException {
        Survey survey = dao.findByTenantAndId(tenant, id).orElseThrow(() -> new CHException(CULTR_SURVEY_NOT_FOUND));
        List<EngineScoreRes> res = new ArrayList<>();
        survey.getEngines().forEach(engineWeight -> {
            res.add(new EngineScoreRes(engineWeight.getName(),
                    engineWeight.getCode(),
                    engineWeight.getWeight(),
                    engineWeight.getScore() / engineWeight.getQuestionCount()));
        });
        return res;
    }

//    @Override
//    public List<SurveyQuestionScore> surveyQuestionScore(String tenant, String id) {
//        List<SurveyQuestionScore> scores = new ArrayList<>();
//        surveyScoreDao.findBySurveyIdAndTenant(id, tenant).ifPresent(sc -> {
//            scores.addAll(sc.getQuestionScores());
//        });
//        return scores;
//    }

    @Override
    public void deleteSurvey(String tenant, String id) throws CHException {
        Survey survey = fetchSurveyByIdAndTenant(id, tenant);
        if (!survey.getStatus().equals(SurveyStatus.DRAFT)) {
            throw new CHException(CULTR_SURVEY_CANNOT_DELETE);
        }
        participantService.deleteBySurveyId(id);
        log.debug("Deleting survey {}", id);
        dao.deleteById(id);

    }

    @Override
    public void editSurvey(SurveyDTO updatedSurvey, String tenant, String id) throws CHException {
        Survey survey = fetchSurveyByIdAndTenant(id, tenant);
        CultureBlock block = blockService.fetchByCode(tenant, updatedSurvey.getBlock());
        log.debug("Editing survey {} with current status {}", id, survey.getStatus());
        SurveyStatus status = survey.getStatus();
        if (!(status.equals(SurveyStatus.DRAFT) || status.equals(SurveyStatus.ERROR))) {
            log.error("Cannot edit survey {} with current status {}", id, survey.getStatus());
            throw new CHException(CULTR_SURVEY_CANNOT_EDIT);
        }

        BeanUtils.copyProperties(updatedSurvey, survey);

        survey.setEngines(new ArrayList<>());
        block.getEngines().forEach(e -> {
            EngineWeight ew = new EngineWeight();
            BeanUtils.copyProperties(e, ew);
            survey.getEngines().add(ew);
        });
        survey.setStatus(status); //Making sure that status cannot be updated in update call

        //Delete previously created participants and recreate
        log.info("Clearing previously created participants and creating all based on edit for survey {} tenant {}", id, tenant);
        participantService.deleteBySurveyId(id);
        participantService.createParticipants(id, tenant,
                updatedSurvey,
                block.getQuestions(),survey.getEngines());
        survey.setPartCount(CollectionUtils.emptyIfNull(updatedSurvey.getSurveyParticipants()).size());
        dao.save(survey);
    }


    /**
     * Status update of survey
     */
    @Override
    public void updateSurveyStatus(String tenant, String id, String newStatus) throws CHException {
        Survey survey = fetchSurveyByIdAndTenant(id, tenant);
        log.debug("Updating status of survey {} from {} to {}", id, survey.getStatus(), newStatus);
        if (survey.getStatus().name().equals(newStatus)) {
            log.warn("updateSurveyStatus(): Old and new status request same for survey {}, skipping update", id);
            return;
        }
        if (config.getStatusMap().get(survey.getStatus().name()).contains(newStatus)) {
            survey.setStatus(SurveyStatus.valueOf(newStatus));
            log.debug("Successfully updated survey {} to {}", survey.getId(), survey.getStatus());
            if (survey.getStatus().compareTo(SurveyStatus.PUBLISHED) == 0) {
                log.debug("Status moved to published. Pushing emails to all participants");
                if(survey.getStartDate() == null){
                    survey.setStartDate(LocalDateTime.now(ZoneOffset.UTC));
                    survey.setStatus(SurveyStatus.STARTED);
                    participantService.updateSurveyStatus(tenant, survey.getId(), SurveyStatus.STARTED);
                    participantService.emailParticipantForSurvey(tenant, survey.getId(), survey);
                }

            }
            dao.save(survey);
        } else {
            log.error("Status update for survey {} from {} to {} not allowed", id, survey.getStatus(), newStatus);
            throw new CHException(CULTR_SURVEY_STATUS_UPDATE_NOT_ALLOWED);
        }
    }

    @Override
    public Map listParticipant(String tenant, String id) throws CHException {
        SurveyRes survey = details(tenant, id);
        List<ParticipantMinRes> participants = new ArrayList<>();
        participantService.findAllParticipantForSurvey(tenant, id).forEach(p -> {
            participants.add(
                    new ParticipantMinRes(
                            p.isSurveyComplete(),
                            String.format(config.getSurveyLinkFormat(), survey.getTenant(), survey.getId(), p.getAccessCode()),
                            p.getEmail()));
        });

        return Map.of("survey_details", survey, "participants", participants);
    }

    @Override
    public ParticipantRes participantDetailsByEmail(String tenant, String id, String email) throws CHException {
        Survey survey = fetchSurveyByIdAndTenant(id, tenant);
        ParticipantRes participantRes = new ParticipantRes();
        participantService.findAllParticipantForSurvey(tenant, id).forEach(p -> {
            if (p.getEmail().equalsIgnoreCase(email)) {
                BeanUtils.copyProperties(p, participantRes);
                participantRes.setLink(String.format(config.getSurveyLinkFormat(),
                        survey.getTenant(),
                        survey.getId(),
                        p.getAccessCode()));
                participantRes.setSurveyName(survey.getName());
                participantRes.setPublisher(survey.getPublisher());
            }
        });

        return participantRes;
    }

    @Override
    public ParticipantRes participantDetails(String tenant, String id, String accessCode) throws CHException {
        log.debug("Fetching participant {} for survey {} and tenant {}", accessCode, id, tenant);
        Participant participant = participantService.findParticipantByAccessCodeForSurvey(id, tenant, accessCode);
        if (participant == null) {
            log.error("Participant for survey {}, tenant {} and accessCode {} not found in database", id, tenant, accessCode);
            throw new CHException(CULTR_SURVEY_PARTICIPANT_NOT_FOUND);
        }

        ParticipantRes participantRes = new ParticipantRes();
        BeanUtils.copyProperties(participant, participantRes);
        participantRes.setLink(String.format(config.getSurveyLinkFormat(),
                tenant,
                id,
                participant.getAccessCode()));

        //To be removed once code is refactored
        participant.getResponseMap().values().forEach(r -> participantRes.getResponses().add(r));
//        participant.getResponseMap().forEach((questionId, r) -> {
//            participantRes.getResponses().add(r);
//        });
        return participantRes;
    }

    @Override
    public List<StaticSurveyQuestion> surveyQuestions(String tenant, String surveyId, String accessCode) throws CHException {
        Survey survey = fetchSurveyByIdAndTenant(surveyId, tenant);
        Participant participant = validateSurveyParticipation(survey, tenant, accessCode);
        List<Participant> participation = participantService
                .findAllForEmployee(
                        tenant,
                        participant.getEmail(),
                        LocalDateTime.now(ZoneOffset.UTC).minusMonths(config.getSmartThreshold())
                );

        Set<String> smartSkipEligibleQuestions = participation.
                stream().
                flatMap(p -> p.getResponseMap().keySet().stream()).collect(Collectors.toSet());

        List<StaticSurveyQuestion> questions = new ArrayList<>();
        survey.getQuestionMap().forEach((code, question) -> {
            if (smartSkipEligibleQuestions.contains(code) && survey.isSmartSkip()) {
                log.debug("Enabling smart skip for question {} for participant {}", code, participant.getEmail());
                question.setSmartSkip(true);
            }
            questions.add(question);
        });
        return questions;
    }

    @Override
    public void addResponse(List<ResponseDTO> responses, String tenant, String surveyId, String accessCode) throws CHException {
        Survey survey = fetchSurveyByIdAndTenant(surveyId, tenant);
        Participant participant = participantService.findParticipantByAccessCodeForSurvey(surveyId, tenant, accessCode);

        //Validate the participant and submitted response
        surveyResponseValidation(responses, participant, survey);

        Map<String, EngineWeight> participantScore = new HashMap<>();
        log.info("Capturing response for participant {} for survey {} and tenant {}", participant.getEmail(), surveyId, tenant);

        for(ResponseDTO r : responses) {
            StaticSurveyQuestion ques = survey.getQuestionMap().get(r.getQuestionCode());
            QuestionManager rm = (QuestionManager)context.getBean(ques.getResponseType());
            rm.validate(r);
            Response response = rm.evaluate(r, ques);
            participant.getResponseMap().put(r.getQuestionCode(), response);

            //Update score
            log.debug("Available engines: {} to be matched with {}", survey.getEngines(), ques.getEngine().getCode());

            //Aggregate the engine score
            EngineWeight partEngineWeight = participantScore.get(ques.getEngine().getCode()) == null ?
                    new EngineWeight().score(0d).questionCount(0) : participantScore.get(ques.getEngine().getCode());
            partEngineWeight.setCode(ques.getEngine().getCode());
            partEngineWeight.setName(ques.getEngine().getName());
            partEngineWeight.setWeight(survey.getEngines().stream().filter(e -> e.getCode().equals(ques.getEngine().getCode())).findFirst().orElseGet(() -> new EngineWeight()).getWeight());
            partEngineWeight.setScore(partEngineWeight.getScore() + response.getScore());
            partEngineWeight.setQuestionCount(partEngineWeight.getQuestionCount() + 1);
            participantScore.put(ques.getEngine().getCode(), partEngineWeight);
            participant.getEngineScore().put(ques.getEngine().getCode(), partEngineWeight);
        }

                //Update the survey level engine score with participant's response evaluation
                survey.getEngines().forEach(ew -> {
                    ew.setScore(ew.getScore() + participant.getEngineScore().get(ew.getCode()).getScore());
                    ew.setQuestionCount(ew.getQuestionCount() + participant.getEngineScore().get(ew.getCode()).getQuestionCount());
                });

        participant.setSurveyComplete(true);
        participant.setResponseTS(LocalDateTime.now(ZoneOffset.UTC));
        participantService.updateParticipant(participant);
        List<String> reportingHierarchy = userService.fetchReportingHierarchy(tenant, participant.getEmail());
        participant.getEngineScore().forEach((engineCode, pe) ->
                esrRecordPublisher.publish(
                        participant.getId()+"__"+engineCode,
                        EngineScoreRecord.newBuilder()
                                .setMetadata(com.cohotz.survey.esr.avro.MetaData.newBuilder()
                                        .setSource(SURVEY_SERVICE_CLIENT)
                                        .setTraceId(MDC.get(LOG_TRACE_ID))
                                .build())
                                .setData(com.cohotz.survey.esr.avro.EngineScore.newBuilder()
                                        .setEmail(participant.getEmail())
                                        .setReportingTo(participant.getReportingTo())
                                        .setTenant(participant.getTenant())
                                        .setEngine(new EngineDetails(engineCode, pe.getName()))
                                        .setBlock(new BlockDetails(survey.getBlock().getCode(), survey.getBlock().getName()))
                                        .setSource("SURVEY")
                                        .setTotalScore(pe.getScore())
                                        .setAverageScore(pe.getScore()/pe.getQuestionCount())
                                        .setCohorts(participant.getCohorts().stream().map(c -> new Cohort(c.getValue(), c.getName())).collect(Collectors.toList()))
                                        .setResponses(pe.getQuestionCount())
                                        .setReportingHierarchy(reportingHierarchy.stream().collect(Collectors.toList()))
                                        .build()
                                ).build()));


        //Update status to IN_PROGRESS on first response by any participant
        if (survey.getStatus().equals(SurveyStatus.PUBLISHED)) {
            survey.setStatus(SurveyStatus.STARTED);
        }
        dao.save(survey);
    }

    @Override
    public void deleteAll() {
        participantService.deleteAll();
        dao.deleteAll();
    }

    private Survey fetchSurveyByIdAndTenant(String id, String tenant) throws CHException {
        Survey survey = dao.findById(id)
                .orElseThrow(() -> new CHException(CULTR_SURVEY_NOT_FOUND));

        if (!survey.getTenant().equals(tenant)) {
            throw new CHException(CULTR_SURVEY_INVALID_TENANT);
        }

        return survey;
    }

    private Participant validateSurveyParticipation(Survey survey, String tenant, String accessCode) throws CHException {
        if (survey.getStatus().surveyClosed()) {
            throw new CHException(CULTR_SURVEY_CLOSED);
        }else if(survey.getStatus().surveyNotStarted()){
            throw new CHException(CULTR_SURVEY_NOT_STARTED);
        }

        if (!survey.getTenant().equals(tenant)) {
            throw new CHException(CULTR_SURVEY_INVALID_TENANT);
        }
        Participant currentParticipant = participantService.findParticipantByAccessCodeForSurvey(survey.getId(), tenant, accessCode);
        if (currentParticipant == null) {
            throw new CHException(CULTR_SURVEY_ACCESS_CODE_INVALID);
        }

        if (currentParticipant.isSurveyComplete()) {
            throw new CHException(CULTR_SURVEY_ALREADY_SUBMITTED);
        }
        return currentParticipant;
    }

    private void surveyResponseValidation(List<ResponseDTO> responses, Participant participant, Survey survey) throws CHException {
        log.debug("Validating Survey response for participant: {}", participant.getEmail());
        if (participant == null) throw new CHException(CULTR_SURVEY_PARTICIPANT_NOT_FOUND);
        if (participant.isSurveyComplete()) throw new CHException(CULTR_SURVEY_ALREADY_SUBMITTED);
        if (survey.getStatus().surveyClosed()) {
            throw new CHException(CULTR_SURVEY_CLOSED);
        }else if(survey.getStatus().surveyNotStarted()){
            throw new CHException(CULTR_SURVEY_NOT_STARTED);
        }

        //Validate for duplicate responses
        if(responses.size() != Set.copyOf(responses).size()) throw new CHException(CULTR_SURVEY_DUPLICATE_ANSWERS);

        //Validate that all mandatory/non-smart skipped questions are answered
        AtomicBoolean missingResponse = new AtomicBoolean(false);
        AtomicBoolean invalidSelection = new AtomicBoolean(false);
        participant.getResponseMap().forEach((q,r) -> {
            QuestionManager qm = (QuestionManager) context.getBean(survey.getQuestionMap().get(q).getResponseType());
            ResponseDTO responseDto = responses.stream().filter(res -> res.getQuestionCode().equals(q)).findFirst().orElse(null);
            invalidSelection.set(qm.validateResponse(responseDto, survey.getQuestionMap().get(q)));
        });
        if(missingResponse.get()) throw new CHException(CULTR_SURVEY_MISSING_MANDATORY_RESPONSE);
        if(invalidSelection.get()) throw new CHException(CULTR_SURVEY_RESPONSE_INVALID_OPTION);
    }
}
