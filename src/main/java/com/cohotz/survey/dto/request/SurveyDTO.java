package com.cohotz.survey.dto.request;

import com.cohotz.survey.model.Survey;
import com.cohotz.survey.model.SurveyStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDTO {

    @NotNull
    @NotEmpty
    private String name;
    private String description;
    private String publisher;
    private String comment;
    private List<String> tags;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @NotNull
    @Future
    private LocalDateTime endDate;
    private SurveyStatus status;
    @NotNull
    protected Survey.ParticipantSource participantSource;
    @NotNull
    private String block;
    private Set<String> participants;
    private long reminderFrequencyInDays;
    @Builder.Default
    private boolean smartSkip =true;
    @Builder.Default
    private Survey.SurveyType type = Survey.SurveyType.CULTURE_ENGINE_STATIC;
}
