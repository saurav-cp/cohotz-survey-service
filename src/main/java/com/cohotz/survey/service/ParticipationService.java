package com.cohotz.survey.service;

import com.cohotz.survey.model.score.CHEntityScore;

import java.time.LocalDate;
import java.util.List;

public interface ParticipationService {
    List<CHEntityScore> engineParticipationTrends(String tenant, List<String> engines, LocalDate from, LocalDate to);
    List<CHEntityScore> engineParticipationCohortStatus(String tenant, List<String> engines, LocalDate from, LocalDate to);
    List<CHEntityScore> engineParticipationCohortTrends(String tenant, List<String> engines, String cohort, String cohortOption, LocalDate from, LocalDate to);

    List<CHEntityScore> experienceParticipationTrends(String tenant, List<String> blocks, LocalDate from, LocalDate to);
    List<CHEntityScore> experienceParticipationCohortStatus(String tenant, List<String> blocks, LocalDate from, LocalDate to);
    List<CHEntityScore> experienceParticipationCohortTrends(String tenant, List<String> blocks, String cohort, String cohortOption, LocalDate from, LocalDate to);
}
