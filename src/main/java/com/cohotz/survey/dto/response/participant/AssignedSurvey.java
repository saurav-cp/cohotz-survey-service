package com.cohotz.survey.dto.response.participant;

import com.cohotz.survey.model.microculture.CohortParticipation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AssignedSurvey {

    public AssignedSurvey() {
        this.cohortParticipation = new ArrayList<>();
    }
    private String surveyId;
    private String surveyName;
    private String accessCode;
    private String link;
    private LocalDateTime dueDate;
    private boolean complete;
    private List engines;
    protected long reminderFrequencyInDays;
    protected LocalDateTime lastReminder;
    protected List<CohortParticipation> cohortParticipation;

    @Override
    public int hashCode() {
        return Objects.hash(surveyId, surveyName);
    }
}
