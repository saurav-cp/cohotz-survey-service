package com.cohotz.survey.model;

import com.cohotz.survey.client.core.model.CultureBlockMin;
import com.cohotz.survey.client.core.model.CultureEngineMin;
import com.cohotz.survey.dto.response.SurveyQuestionScore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document("cp_survey_score")
@Sharded(shardKey = {"tenant"})
@Getter
@Setter
public class SurveyScore {
    public SurveyScore(){
        questionScores = new ArrayList<>();
        engines = new ArrayList<>();
    }
    @Id
    protected String id;
    @Field("survey_id")
    protected String surveyId;
    protected String tenant;
    @Field("creation_dt")
    protected LocalDateTime creationDate;
    protected CultureBlockMin block;
    protected List<CultureEngineMin> engines;
    @Field("ques_scores")
    protected List<SurveyQuestionScore> questionScores;
}
