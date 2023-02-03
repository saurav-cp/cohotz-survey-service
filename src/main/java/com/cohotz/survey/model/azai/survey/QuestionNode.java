package com.cohotz.survey.model.azai.survey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionNode {
    int level;
    String question;
    QuestionNode positive, negative;

    @JsonCreator
    public QuestionNode(@JsonProperty("level") final int level, @JsonProperty("question") final String question) {
        this.level = level;
        this.question = question;
    }
}
