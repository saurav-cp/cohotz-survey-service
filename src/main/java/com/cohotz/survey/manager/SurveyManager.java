package com.cohotz.survey.manager;

import com.cohotz.survey.config.SurveyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SurveyManager {

    @Autowired
    SurveyConfiguration config;

    public List<String> nextStatuses(String currentState) {
        return config.getStatusMap().get(currentState);
    }
}
