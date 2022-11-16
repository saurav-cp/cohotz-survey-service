package com.cohotz.survey.dto.response;

import com.cohotz.survey.model.SurveyStatus;
import com.cohotz.survey.model.engine.WeightedEngineScore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SurveyRes {
    private String id;
    private String name;
    private String description;
    private String publisher;
    private String comment;
    private String tenant;
    private boolean smartSkip;
    private List<String> tags;
    private Map<String, Double> engineScore = new HashMap<>();
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private SurveyStatus status;
    private String block; //Id and version of the Cultr Block used
    private List<ParticipantMinRes> participants = new ArrayList<>();
    private List<QuestionMinRes> questions = new ArrayList<>();
    private String formula;
    private long reminderFrequencyInDays;
    private LocalDateTime lastReminder;
    private List<WeightedEngineScore> engines;
    private String error;
    protected long partCount;
    protected long responseCount;
}
