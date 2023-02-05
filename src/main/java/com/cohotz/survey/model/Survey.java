package com.cohotz.survey.model;

import com.cohotz.survey.model.engine.WeightedEngineScore;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.cohotz.boot.model.common.CohotzEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "ch_survey")
@Sharded(shardKey = { "tenant" })
@ToString
@Setter
@Getter
public class Survey {
    public Survey(){
        this.questionMap = new HashMap<>();
        this.engines = new ArrayList<>();
        this.type = SurveyType.CULTURE_ENGINE_STATIC;
    }
    @Id
    protected String id;
    protected String name;
    @Field("desc")
    protected String description;
    @Field("pub")
    protected String publisher;
    @Field("pub_name")
    protected String publisherName;
    protected String comment;
    protected String tenant;
    protected List<String> tags;
    @Field("start_dt")
    protected LocalDateTime startDate;
    @Field("end_dt")
    protected LocalDateTime endDate;
    @Field("comp_dt")
    protected LocalDateTime completedDate;
    protected SurveyStatus status;
    protected CohotzEntity block;
    @Field("questions")
    protected Map<String, StaticSurveyQuestion> questionMap;
    protected String formula;
    @Field("rem_freq_in_d")
    protected long reminderFrequencyInDays;
    @Field("lst_rem_dt")
    protected LocalDateTime lastReminder;
    private List<WeightedEngineScore> engines;
    private String error;
    @Field("smart_skip")
    protected boolean smartSkip;
    @Field("part_count")
    protected long partCount;
    @Field("res_count")
    protected long responseCount;
    protected SurveyType type;
    @Field("part_source")
    protected ParticipantSource participantSource;
    @Field("created_ts")
    protected LocalDateTime createdTS = LocalDateTime.now();
    public enum SurveyType {
        CULTURE_ENGINE_STATIC, CULTURE_ENGINE_RUBICA, ENPS, SPOT, AUTOMATIC, ENGAGEMENT
    }

    public enum ParticipantSource {
        DIRECT_REPORTS, MANUAL, TEAM
    }
}
