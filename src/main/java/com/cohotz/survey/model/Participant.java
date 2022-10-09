package com.cohotz.survey.model;

import com.cohotz.survey.client.core.model.CultureBlockMin;
import com.cohotz.survey.client.core.model.CultureEngineMin;
import com.cohotz.survey.client.core.model.EngineWeight;
import com.cohotz.survey.model.response.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.cohotz.boot.model.CHBaseModel;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document("cp_survey_participant")
@Sharded(shardKey = "tenant")
public class Participant extends CHBaseModel {

    public Participant(String email, String reportingTo, String surveyId, String surveyName, String tenant, LocalDateTime dueDate){
        this.surveyId = surveyId;
        this.surveyName = surveyName;
        this.dueDate = dueDate;
        this.accessCode = UUID.randomUUID().toString();
        this.surveyComplete = false;
        this.scoreAccumulated = false;
        this.emailSent = false;
        this.email = email;
        this.reportingTo = reportingTo;
        this.setResponseMap(new HashMap<>());
        this.setEngineScore(new HashMap<>());
        this.engines = new ArrayList<>();
        this.tenant = tenant;
    }

    @Id
    private String id;

    @Field("survey_id")
    @Indexed
    private String surveyId;

    @Field("survey_name")
    @Indexed
    private String surveyName;

    @Field("survey_status")
    private SurveyStatus surveyStatus;

    private CultureBlockMin block;

    @Field("access_cd")
    private String accessCode;

    private String email;

    @Field("reporting_to")
    private String reportingTo;

    @Field("email_sent")
    private boolean emailSent;

    @Field("survey_complete")
    private boolean surveyComplete;

    @Field("score_accumulated")
    private boolean scoreAccumulated;

    @Field("responses")
    private Map<String, Response> responseMap;

    @Field("score")
    private Map<String, EngineWeight> engineScore;

    private List<Cohort> cohorts;

    private List<CultureEngineMin> engines;

    @Field("due_dt")
    private LocalDateTime dueDate;

//    @Field("created_ts")
//    @CreatedDate
//    private LocalDateTime createdTS;

    @Field("response_ts")
    private LocalDateTime responseTS;

    @Field("rem_freq_in_d")
    protected long reminderFrequencyInDays;

    @Field("lst_rem_dt")
    protected LocalDateTime lastReminder;
}
