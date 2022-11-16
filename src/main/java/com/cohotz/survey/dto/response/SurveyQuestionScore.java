package com.cohotz.survey.dto.response;

import com.cohotz.survey.model.question.ResponseInsight;
import lombok.Data;
import org.cohotz.boot.model.common.CohotzEntity;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class SurveyQuestionScore {
    public SurveyQuestionScore(double max, double min){
        this.min = min;
        this.max = max;
    }
    @Field("question_code")
    private String questionCode;
    private String question;
    private CohotzEntity engine;
    private double score;
    private int participants;
    private double min;
    private double max;
    private ResponseInsight insight;
}
