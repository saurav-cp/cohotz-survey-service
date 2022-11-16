package com.cohotz.survey.service.impl;

import com.cohotz.survey.dao.ParticipantDao;
import com.cohotz.survey.model.score.CHEntityScore;
import com.cohotz.survey.service.ParticipationService;
import com.cohotz.survey.utils.GraphUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipantDao dao;

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
}
