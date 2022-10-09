package com.cohotz.survey.service;

import com.cohotz.survey.model.Survey;

public interface SurveyUpdateTask {
    void update();
    void update(String tenant);
}
