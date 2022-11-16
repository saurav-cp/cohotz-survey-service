package com.cohotz.survey.model.engine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightedEngineScore {
    private String name;
    private String code;
    private int weight;
    private double score = 0;
    @Field("question_count")
    private int questionCount = 0;
    private double total;

    public WeightedEngineScore(String name, String code, int weight) {
        this.name = name;
        this.code = code;
        this.weight = weight;
    }
}
