package com.cohotz.survey.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChoiceResponse extends Response{
    private List<Integer> selections;
}
