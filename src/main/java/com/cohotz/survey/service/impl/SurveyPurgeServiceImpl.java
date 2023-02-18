package com.cohotz.survey.service.impl;

import com.cohotz.survey.dao.ParticipantDao;
import com.cohotz.survey.dao.SurveyDao;
import com.cohotz.survey.dao.UserReporteeParticipationCacheDao;
import lombok.extern.slf4j.Slf4j;
import org.cohotz.boot.error.CHException;
import org.cohotz.boot.service.PurgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(1)
public class SurveyPurgeServiceImpl implements PurgeService {

    @Value("${cohotz.data.purge:true}")
    private boolean purge;

    @Autowired
    SurveyDao surveyDao;

    @Autowired
    ParticipantDao participantDao;

    @Autowired
    UserReporteeParticipationCacheDao cacheDao;

    public void execute(String tenant) throws CHException {
        log.info("-------------------------------------------------------------");
        log.info("Purge environment flag is true. Purging Survey data for [{}]. Point of no return!", tenant);
        log.info("-------------Reportee Cache Data Purge-----------------------");
        cacheDao.deleteByTenant(tenant);
        log.info("--------------------Participant Purge------------------------");
        participantDao.deleteByTenant(tenant);
        log.info("----------------------Survey Purge---------------------------");
        surveyDao.deleteByTenant(tenant);
        log.info("-------------------------------------------------------------");
        log.info("Survey data purge complete for [{}]", tenant);
        log.info("-------------------------------------------------------------");
    }
}
