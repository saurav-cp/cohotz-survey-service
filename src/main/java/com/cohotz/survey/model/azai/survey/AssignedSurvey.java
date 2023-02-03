package com.cohotz.survey.model.azai.survey;

import com.cohotz.survey.model.engine.WeightedEngineScore;
import com.cohotz.survey.model.microculture.CohortParticipation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AssignedSurvey {

    public AssignedSurvey() {
        this.cohortParticipation = new ArrayList<>();
    }

    @Field("survey_id")
    private String surveyId;
    @Field("survey_name")
    private String surveyName;
    @Field("access_cd")
    private String accessCode;
    private String link;
    @Field("due_dt")
    private LocalDateTime dueDate;
    private boolean complete;
    private Map<String, WeightedEngineScore> engines;
    @Field("rem_freq_in_d")
    protected long reminderFrequencyInDays;
    @Field("lst_rem_dt")
    protected LocalDateTime lastReminder;
    protected List<CohortParticipation> cohortParticipation;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignedSurvey that = (AssignedSurvey) o;
        return surveyId.equals(that.surveyId) && surveyName.equals(that.surveyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surveyId, surveyName);
    }
}
