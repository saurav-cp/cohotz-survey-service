package com.cohotz.survey.dto.response;

import com.cohotz.survey.client.core.model.CultureEngineMin;
import com.cohotz.survey.model.question.ResponseInsight;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class SurveyQuestionScore {
    public SurveyQuestionScore(double max, double min){
        this.min = min;
        this.max = max;
    }
    @Field("question_code")
    private String questionCode;
    private String question;
    private CultureEngineMin engine;
    private double score;
    private int participants;
    private double min;
    private double max;
    private ResponseInsight insight;
}
