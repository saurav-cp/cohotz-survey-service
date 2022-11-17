package com.cohotz.survey.service.impl;

import com.cohotz.survey.client.api.UserService;
import com.cohotz.survey.client.profile.model.UserMinRes;
import com.cohotz.survey.dao.ParticipantDao;
import com.cohotz.survey.dao.UserReporteeParticipationCacheDao;
import com.cohotz.survey.model.microculture.UserReporteeParticipation;
import com.cohotz.survey.model.microculture.UserReporteeParticipationCache;
import com.cohotz.survey.model.score.CHEntityScore;
import com.cohotz.survey.service.ParticipationService;
import com.cohotz.survey.utils.GraphUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cohotz.boot.error.CHException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final UserService userService;

    private final ParticipantDao dao;

    private final UserReporteeParticipationCacheDao cacheDao;

    @Value("${cohotz.micro-culture.reportee-threshold:5}")
    int reporteeThreshold;

    @Value("${cohotz.micro-culture.caching:false}")
    Boolean caching;

    @Override
    public List<CHEntityScore> engineParticipationTrends(String tenant, List<String> engines, LocalDate from, LocalDate to) {
        List<CHEntityScore> trends = new ArrayList<>();
        dao.engineParticipationTrends(tenant, engines, from, to)
                .forEach(engine ->
                        trends.add(new CHEntityScore(engine.getName(), GraphUtils.fillTimeSeriesGap(from, to, engine.getScores())))
                );
        return trends;
    }

    @Override
    public List<CHEntityScore> engineParticipationCohortStatus(String tenant, List<String> engines, LocalDate from, LocalDate to) {
        return dao.engineParticipationCohortStatus(tenant, engines, from, to);
    }

    @Override
    public List<CHEntityScore> engineParticipationCohortTrends(String tenant, List<String> engines, String cohort, String cohortOption, LocalDate from, LocalDate to) {
        CHEntityScore trends = new CHEntityScore();
        List<CHEntityScore> data = dao.engineParticipationCohortTrends(tenant, engines, cohort, cohortOption, from, to);
        if(data.size() > 0) {
            trends.setName(data.get(0).getName());
            trends.setScores(GraphUtils.fillTimeSeriesGap(from, to, data.get(0).getScores()));
        }
        return List.of(trends);
    }

    @Override
    public List<CHEntityScore> experienceParticipationTrends(String tenant, List<String> blocks, LocalDate from, LocalDate to) {
        List<CHEntityScore> trends = new ArrayList<>();
        dao.experienceParticipationTrends(tenant, blocks, from, to).forEach(block -> {
            trends.add(new CHEntityScore(block.getName(), GraphUtils.fillTimeSeriesGap(from, to, block.getScores())));
        });
        return trends;
    }

    @Override
    public List<CHEntityScore> experienceParticipationCohortStatus(String tenant, List<String> blocks, LocalDate from, LocalDate to) {
        return dao.experienceParticipationCohortStatus(tenant, blocks, from, to);
    }

    @Override
    public List<CHEntityScore> experienceParticipationCohortTrends(String tenant, List<String> blocks, String cohort, String cohortOption, LocalDate from, LocalDate to) {
        CHEntityScore trends = new CHEntityScore();
        List<CHEntityScore> data = dao.experienceParticipationCohortTrends(tenant, blocks, cohort, cohortOption, from, to);
        if(data.size() > 0) {
            trends.setName(data.get(0).getName());
            trends.setScores(GraphUtils.fillTimeSeriesGap(from, to, data.get(0).getScores()));
        }
        return List.of(trends);
    }

    @Override
    public List<UserReporteeParticipation> microCulture(String tenant, String block, LocalDate from, LocalDate to) {
        List<UserReporteeParticipation> participations = new CopyOnWriteArrayList<>();
        if (caching) {
            cacheDao.findByTenantAndCodeAndStartAndEnd(tenant, block, from, to)
                    .ifPresentOrElse(cache -> {
                        log.debug("Cached Micro Culture participation found for {} from {} to {}", tenant, from, to);
                    }, () -> { //If not found in cache
                        log.debug("Cache miss for Micro Culture score found for {} from {} to {}", tenant, from, to);
                        populateMicroCulture(tenant, block, from, to, participations);
                        cacheDao.save(new UserReporteeParticipationCache(participations, tenant, block, from, to));
                        log.debug("Cache entry made for Micro Culture score found for {} from {} to {}", tenant, from, to);
                    });
        } else {
            populateMicroCulture(tenant, block, from, to, participations);
        }
        return participations;
    }

    private void populateMicroCulture(String tenant, String block, LocalDate from, LocalDate to, List<UserReporteeParticipation> participations) {
        try {
            for(UserMinRes u : userService.fetchByTenant(tenant)) {
                log.debug("Calculating participation for {}", u.getEmail());
                List<String> reportees;
                reportees = userService.allReportees(u.getTenant(), u.getEmail());
                log.debug("Number of reportee for [{}] is [{}]. Threshold : [{}]", u.getEmail(), reportees.size(), reporteeThreshold);
                if (reportees.size() >= reporteeThreshold) {
                    populateMicroCultureForUser(tenant, block, from, to, participations, u);
                } else {
                    participations.add(new UserReporteeParticipation(block, block, 0.0, u));
                }
            }
        } catch (CHException e) {
            log.error("Error while populating block score for [{}]: [{}]", tenant, e.getError().getDescription());
        }
    }

    private void populateMicroCultureForUser(String tenant, String engine, LocalDate from, LocalDate to, List<UserReporteeParticipation> scores, UserMinRes user) {
        DecimalFormat df = new DecimalFormat("###.##");
        UserReporteeParticipation score = dao.reporteeParticipation(tenant, engine, from, to, user.getEmail())
                .orElse(new UserReporteeParticipation(engine, engine, 0));
        score.setEmail(user.getEmail());
        score.setName(user.getFirstName() +" "+user.getLastName());
        score.setReportingTo(user.getReportingTo());
        score.setDepartment(user.getDepartment());
        score.setScore(Double.parseDouble(df.format(score.getScore())));
        score.setThresholdMet(true);
        scores.add(score);
    }
}
