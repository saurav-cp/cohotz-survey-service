package com.cohotz.survey.service;

public interface ScheduledSurveyService {
    void createRequest(String tenant, String email, String publisher, String block, boolean self);
    void createSurveyTask();
    void createSurvey(String tenant);
}
