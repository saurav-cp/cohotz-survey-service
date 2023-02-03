package com.cohotz.survey.model.azai.session;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionContext {
    private String type;
    private String engine;
    private String category;
}
