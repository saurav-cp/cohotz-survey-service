package com.cohotz.survey.model.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SurveyScheduler {
    private boolean enabled;
    private long frequency;
    private List<String> tenants;
}
