package com.cohotz.survey.service.impl;

import com.cohotz.survey.client.api.UserService;
import com.cohotz.survey.client.core.model.CultureEngineMin;
import com.cohotz.survey.client.core.model.EngineWeight;
import com.cohotz.survey.client.core.model.Question;
import com.cohotz.survey.client.profile.model.UserRes;
import com.cohotz.survey.config.SurveyConfiguration;
import com.cohotz.survey.dao.ParticipantDao;
import com.cohotz.survey.dto.request.SurveyDTO;
import com.cohotz.survey.model.Cohort;
import com.cohotz.survey.model.Participant;
import com.cohotz.survey.model.Survey;
import com.cohotz.survey.model.SurveyStatus;
import com.cohotz.survey.model.response.Response;
import com.cohotz.survey.service.MailService;
import com.cohotz.survey.service.SurveyParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.cohotz.boot.error.CHException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static com.cohotz.survey.utils.ProfileUtils.getYearRange;

@Service
@Slf4j
public class SurveyParticipantServiceImpl implements SurveyParticipantService {

    @Autowired
    ParticipantDao dao;

    @Autowired
    SurveyConfiguration config;

    @Autowired
    MailService mailService;

    @Autowired
    UserService userService;

    @Override
    public void createParticipantsSync(String surveyId, String tenant, SurveyDTO surveyDto, List<Question> questions, List<EngineWeight> engines) throws CHException {
        createParticipants(surveyId, tenant, surveyDto, questions, engines);
    }

    @Override
    @Async
    public void createParticipants(String surveyId, String tenant, SurveyDTO surveyDto, List<Question> questions, List<EngineWeight> engines) throws CHException {
        Set<String> participantEmails;
        switch (surveyDto.getParticipantSource()) {
            case TEAM:
            case MANUAL:
                participantEmails = surveyDto.getSurveyParticipants();
                break;
            case DIRECT_REPORTS:
                participantEmails = CollectionUtils
                        .emptyIfNull(userService.usersByReporting(tenant, surveyDto.getPublisher()))
                        .stream().map(u -> u.getEmail()).collect(Collectors.toSet());
                break;
            default:
                participantEmails = new HashSet<>();
        }
        participantEmails.forEach(email -> {
            try {
                createParticipant(tenant, surveyId, surveyDto, email, questions, engines);
            }catch (CHException e) {
                log.error("Error while creating participant. Skipping participant: [{}] [{}]",
                        e.getError().getCode(), e.getError().getDescription());
            }
        });
    }

    @Override
    @Async
    public Participant createParticipant(String tenant, String surveyId, SurveyDTO surveyDto, String email, List<Question> questions, List<EngineWeight> engines) throws CHException {
        log.debug("Creating participant {} for survey {}", email, surveyId);
        UserRes user = userService.fetchByTenantAndEmail(tenant, email);
        Participant participant = new Participant(email, user.getReportingToEmail(), surveyId, surveyDto.getName(), user.getTenant(), surveyDto.getEndDate());
        List<Participant> participation = findAllForEmployee(
                tenant,
                user.getEmail(),
                LocalDateTime.now(ZoneOffset.UTC).minusMonths(config.getSmartThreshold()));

        Map<String, Response> smartSkipEligibleQuestions = new HashMap<>();
        participation.forEach(p -> smartSkipEligibleQuestions.putAll(p.getResponseMap()));
        questions.forEach(q -> {
            Response response = new Response();
            Response recentResponse = smartSkipEligibleQuestions.get(q.getPoolQuesReferenceCode());
            if (recentResponse != null && surveyDto.isSmartSkip()) {
                log.debug("Recent response for question {} found for participant {}. Auto updating response", q.getPoolQuesReferenceCode(), user.getEmail());
                BeanUtils.copyProperties(recentResponse, response);
                response.setSmartSkip(true);
            }
            participant.getResponseMap().put(q.getPoolQuesReferenceCode(), response);
        });

        //Create blank engines for participant
        engines.forEach(e -> {
            participant.getEngineScore().put(e.getCode(), new EngineWeight());
            participant.getEngines().add(new CultureEngineMin().name(e.getName()).code(e.getCode()));
        });

        participant.setSurveyStatus(SurveyStatus.DRAFT);
        participant.setCohorts(fetchCohorts(user));

        return dao.save(participant);
    }

    @Override
    public Participant updateParticipant(Participant participant) {
        log.debug("Updating participant {}", participant);
        return dao.save(participant);
    }

    @Override
    public Participant updateScoreAccFlag(String participantId) {
        Participant participant = dao.findById(participantId).get();
        participant.setScoreAccumulated(true);
        return dao.save(participant);
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
                    mailService.sendSurvey(survey, p.getEmail(), String.format(
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
    public void updateSurveyStatus(String tenant, String surveyId, SurveyStatus status) {
        dao.findByTenantAndSurveyId(tenant, surveyId).forEach(p -> {
            log.debug("Updating status for participant: {}", p.getEmail());
            p.setSurveyStatus(status);
            dao.save(p);
        });
    }

    @Override
    public List<Participant> findAllForEmployee(String tenant, String email, LocalDateTime responseTS) {
        return dao.findByTenantAndEmailAndResponseTSGreaterThanEqual(tenant, email, responseTS);
    }

//    @Override
//    public List<AssignedSurvey> findAssignedSurveys(boolean pending) {
//        User currentUser = (User)((Map) SecurityContextHolder.getContext().getAuthentication().getDetails()).get(CURRENT_USER);
//        //List<AssignedSurvey> assignedSurveys = new ArrayList<>();
//        return findAllForEmployee(currentUser.getTenant(), currentUser.getEmail())
//                .stream()
//                .filter(p -> pending ? (!p.isSurveyComplete() && p.getDueDate().isAfter(LocalDateTime.now(ZoneOffset.UTC))) : true)
//                .filter(p -> p.getSurveyStatus().inProgress())
//                .map(p -> AssignedSurvey.builder().surveyId(p.getSurveyId()).surveyName(p.getSurveyName())
//                        .accessCode(p.getAccessCode())
//                        .link(String.format(config.getSurveyLinkFormat(),currentUser.getTenant(),p.getSurveyId(),p.getAccessCode()))
//                        .dueDate(p.getDueDate()).complete(false).build()).collect(Collectors.toList());
//    }

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
    public void updateReminder(String tenant, String surveyId, String accessCode) throws CHException {
        dao.findBySurveyIdAndTenantAndAccessCode(surveyId, tenant, accessCode)
                .ifPresent(p -> {
                    p.setLastReminder(LocalDateTime.now(ZoneOffset.UTC));
                    dao.save(p);
                });
    }

//    @Override
//    public double participation(String tenant, String surveyId) {
//        List<Participant> participants = dao.findByTenantAndSurveyId(tenant, surveyId);
//        long completed = participants.stream().filter(p -> p.isSurveyComplete()).count();
//        long total = participants.size();
//        log.debug("Survey {} has total {} participants. Out of which {} have provided response so far." +
//                " Participation percentage {}", surveyId, total, completed, (completed*100.0)/total);
//        return (completed*100.0)/total;
//    }

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
                e.printStackTrace();
            }
        });
        return cohorts;
    }
}
