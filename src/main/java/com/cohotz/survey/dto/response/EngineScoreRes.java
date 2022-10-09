package com.cohotz.survey.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EngineScoreRes {
    private String name;
    private String code;
    private int weight;
    private double score;
}
