package com.cohotz.survey.model.azai.survey;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

@Getter
public class QuestionTree {

    @JsonUnwrapped
    QuestionNode root;

    public QuestionTree(){
        root = null;
    }

    public QuestionTree(QuestionNode node){
        this.root = node;
    }
}
