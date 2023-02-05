package com.cohotz.survey.service.impl;

import com.cohotz.survey.client.api.CultureBlockService;
import com.cohotz.survey.client.api.QuestionPoolService;
import com.cohotz.survey.client.api.UserService;
import com.cohotz.survey.client.core.model.CultureBlock;
import com.cohotz.survey.client.core.model.PoolQuestion;
import com.cohotz.survey.client.core.model.Question;
import com.cohotz.survey.client.core.model.WeightedCultureEngine;
import com.cohotz.survey.client.profile.model.UserRes;
import com.cohotz.survey.config.SurveyConfiguration;
import com.cohotz.survey.dao.SurveyDao;
import com.cohotz.survey.dto.request.SurveyDTO;
import com.cohotz.survey.dto.response.*;
import com.cohotz.survey.manager.QuestionManager;
import com.cohotz.survey.model.Participant;
import com.cohotz.survey.model.Survey;
import com.cohotz.survey.model.SurveyStatus;
import com.cohotz.survey.model.engine.WeightedEngineScore;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.service.MailService;
import com.cohotz.survey.service.SurveyParticipantService;
import com.cohotz.survey.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cohotz.boot.error.CHException;
import org.cohotz.boot.model.common.CohotzEntity;
import org.cohotz.boot.utils.RequestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cohotz.survey.error.ServiceCHError.*;

@Service
@Slf4j
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    SurveyDao dao;

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

    @Autowired
    ApplicationContext context;

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
    public List<SurveyMinRes> fetchAll(String tenant, List<String> statuses) {
        return dao.findByTenantAndStatusIn(tenant, statuses);
    }

    @Override
    public List<Survey> fetchAllForPublisher(String tenant, String email) {
        return dao.findByTenantAndPublisher(tenant, email);
    }

    @Override
    public void createSurvey(final SurveyDTO dto, String tenant, String publisher) throws CHException {
        createSurveySync(dto, tenant, publisher);
    }

    @Override
    public Survey createSurveySync(final SurveyDTO dto, String tenant, String pub) throws CHException {
        Survey survey = new Survey();
        BeanUtils.copyProperties(dto, survey);
        String email = RequestUtils.email(dto.getPublisher());
        UserRes publisher = userService.fetchByTenantAndEmail(tenant, email);
        CultureBlock block = blockService.fetchByCode(tenant, dto.getBlock());
        for(Question q : block.getQuestions()) {
            PoolQuestion poolQuestion = questionService.fetchByTenantAndCode(tenant, q.getPoolQuesReferenceCode());
            log.debug("Creating question {} for survey {} from block {} in position {}"
                    , poolQuestion.getCode(), survey.getName(), dto.getBlock(), q.getPosition());
            QuestionManager qm = (QuestionManager) context.getBean(poolQuestion.getResponseType());
            survey.getQuestionMap().put(poolQuestion.getCode(), qm.createSurveyQuestion(q, poolQuestion));
        }

        block.getEngines().forEach(e -> {
            WeightedCultureEngine ew = new WeightedCultureEngine();
            BeanUtils.copyProperties(e, ew);
            survey.getEngines().add(new WeightedEngineScore(ew.getName(), ew.getCode(), ew.getWeight()));
        });
        survey.setType(block.getType());
        survey.setFormula(block.getFormula());
        survey.setStatus(SurveyStatus.DRAFT);
        survey.setLastReminder(LocalDateTime.now());
        survey.setTenant(tenant);
        survey.setBlock(new CohotzEntity(block.getName(), block.getCode()));
        survey.setPartCount(CollectionUtils.emptyIfNull(dto.getParticipants()).size());
        survey.setCompletedDate(dto.getEndDate());
        survey.setPublisher(email);
        survey.setPublisherName(publisher.getFirstName() +" "+ publisher.getLastName());

        log.debug("Creating survey: {}", survey);
        Survey s = dao.save(survey);

        log.info("Creating participants for survey {} for pub {} for type", s.getId(), email, s.getParticipantSource());
        createParticipants(s, dto.getParticipants(), dto.getParticipantSource());
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

        List<WeightedEngineScore> engineWeights = new ArrayList<>();
        survey.getEngines().forEach(engine -> {
            WeightedEngineScore engineWeight = new WeightedEngineScore();
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
            WeightedEngineScore ew = new WeightedEngineScore();
            BeanUtils.copyProperties(e, ew);
            survey.getEngines().add(ew);
        });
        survey.setStatus(status); //Making sure that status cannot be updated in update call

        //Delete previously created participants and recreate
        log.info("Clearing previously created participants and creating all based on edit for survey {} tenant {}", id, tenant);
        participantService.deleteBySurveyId(id);
        createParticipants(survey, updatedSurvey.getParticipants(), updatedSurvey.getParticipantSource());
        survey.setPartCount(CollectionUtils.emptyIfNull(updatedSurvey.getParticipants()).size());
        dao.save(survey);
    }

    private void createParticipants(Survey survey, Set<String> participants, Survey.ParticipantSource source)
            throws CHException {
        switch (source){
            case TEAM:
            case MANUAL:
                participantService.createParticipants(survey, participants);
                break;
            case DIRECT_REPORTS:
                participantService.createParticipants(survey);
                break;
            default:
                throw new CHException(CH_SURVEY_PUBLISHER_NOT_FOUND);
        }
    }


    /**
     * Status update of survey
     */
    @Override
    public void updateStatus(String tenant, String id, String newStatus) throws CHException {
        Survey survey = fetchSurveyByIdAndTenant(id, tenant);
        log.debug("Updating status of survey {} from {} to {}", id, survey.getStatus(), newStatus);
        if (survey.getStatus().name().equals(newStatus)) {
            log.warn("updateSurveyStatus(): Old and new status request same for survey {}, skipping update", id);
            return;
        }
        if (config.getStatusMap().get(survey.getStatus().name()).contains(newStatus)) {
            //Directly move to STARTED instead of PUBLISHED if start date not defined or is in the past
            if (SurveyStatus.PUBLISHED.equals(newStatus) || SurveyStatus.STARTED.equals(newStatus)) {
                if(survey.getStartDate() == null || LocalDateTime.now(ZoneOffset.UTC).isAfter(survey.getStartDate())){
                    survey.setStartDate(LocalDateTime.now(ZoneOffset.UTC));
                    survey.setStatus(SurveyStatus.STARTED);
                    participantService.updateStatus(tenant, survey.getId(), SurveyStatus.STARTED);
                    participantService.emailParticipantForSurvey(survey);
                }
            }else {
                survey.setStatus(SurveyStatus.valueOf(newStatus));
                participantService.updateStatus(tenant, survey.getId(), survey.getStatus());
            }
            log.debug("Survey [{}] status updated to [{}], pushing emails to participants", survey.getId(), survey.getStatus());
            dao.save(survey);
        } else {
            log.error("Survey [{}] status update from [{}] to [{}] not allowed", id, survey.getStatus(), newStatus);
            throw new CHException(CULTR_SURVEY_STATUS_UPDATE_NOT_ALLOWED);
        }
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
}
