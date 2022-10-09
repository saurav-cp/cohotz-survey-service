package com.cohotz.survey.dto.response;

import com.cohotz.survey.client.core.model.PoolQuestion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionMinRes {
    private String id;
    private int position;
    private String question;
    private String responseType;
    private String engine;
    private boolean skippable;
    private double weight;
    private double score;
    private int responseCount;
}
