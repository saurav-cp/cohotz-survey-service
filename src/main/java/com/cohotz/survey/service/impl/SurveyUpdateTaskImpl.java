package com.cohotz.survey.service.impl;

import com.cohotz.survey.client.api.QuestionPoolService;
import com.cohotz.survey.config.SurveyConfiguration;
import com.cohotz.survey.dao.SurveyDao;
import com.cohotz.survey.dto.response.SurveyMinRes;
import com.cohotz.survey.model.Survey;
import com.cohotz.survey.model.SurveyStatus;
import com.cohotz.survey.service.SurveyParticipantService;
import com.cohotz.survey.service.SurveyService;
import com.cohotz.survey.service.SurveyUpdateTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
@Slf4j
public class SurveyUpdateTaskImpl implements SurveyUpdateTask {

    @Autowired
    SurveyConfiguration configuration;

    @Autowired
    SurveyService surveyService;

    @Autowired
    SurveyDao surveyDao;

    @Autowired
    SurveyParticipantService participantService;

    @Autowired
    QuestionPoolService questionService;

    //@Scheduled(cron = "0 3 * * * ?") //Should run every day at 2AM UTC
    @Scheduled(initialDelay = 300000,fixedDelay = 300000)
    @Override
    public void update() {
        configuration.getScheduler().getTenants().forEach(this::update);
    }

    public void update(String tenant) {
        List<SurveyMinRes> surveys = surveyService.fetchAllIncomplete(tenant);
        surveys.forEach(s ->
                surveyDao.findByTenantAndId(tenant, s.getId()).ifPresent(survey -> {
            log.debug("START: Auto update process for survey {}", survey.getId());
            survey.setPartCount(participantService.surveyParticipantCount(tenant, survey.getId()));

            updateStatus(survey);

            surveyDao.save(survey);
            log.debug("END: Auto update process for survey {}", survey.getId());
        }));
    }


    private void updateStatus(Survey survey){
        //Update survey status to STARTED and trigger emails
        if (survey.getStatus().equals(SurveyStatus.PUBLISHED)
                && survey.getStartDate().isBefore(LocalDateTime.now(ZoneOffset.UTC))) {
            survey.setStatus(SurveyStatus.STARTED);
            participantService.emailParticipantForSurvey(survey.getTenant(), survey.getId(), survey);
        }

        //If Survey has been published with no valid participants, move the status to error
        if (survey.getPartCount() == 0) survey.setStatus(SurveyStatus.NO_VALID_PARTICIPANTS_ERROR);

        //If all participants have provided response then mark the survey as complete
        if (survey.getResponseCount() == survey.getPartCount() && survey.getPartCount() != 0){
            survey.setCompletedDate(LocalDateTime.now(ZoneOffset.UTC));
            survey.setStatus(SurveyStatus.MARKED_COMPLETE);
            participantService.updateSurveyStatus(survey.getTenant(), survey.getId(), SurveyStatus.MARKED_COMPLETE);
            //createScore(survey);
        }

        //If end date is in the past. Mark the survey as auto complete. Irrespective of responses
        if (survey.getEndDate().isBefore(LocalDateTime.now(ZoneOffset.UTC))){
            if(survey.getPartCount() != 0) {
                survey.setStatus(SurveyStatus.AUTO_COMPLETE);
                participantService.updateSurveyStatus(survey.getTenant(), survey.getId(), SurveyStatus.AUTO_COMPLETE);
                //createScore(survey);
            }
            else {
                survey.setStatus(SurveyStatus.NO_RESPONSE_COMPLETE);
                participantService.updateSurveyStatus(survey.getTenant(), survey.getId(), SurveyStatus.NO_RESPONSE_COMPLETE);
            }
        }
    }

}
