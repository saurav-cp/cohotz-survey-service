package com.cohotz.survey.service;

import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.dto.request.SurveyDTO;
import com.cohotz.survey.dto.response.*;
import com.cohotz.survey.model.Survey;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import org.cohotz.boot.error.CHException;

import java.util.List;
import java.util.Map;

public interface SurveyService {


    List<SurveyMinRes> fetchAll(String tenant, String status, String search);
    List<SurveyMinRes> fetchAll(String tenant, List<String> status);
    List<Survey> fetchAllForPublisher(String tenant, String email);

    void createSurvey(final SurveyDTO survey, String tenant, String publisher) throws CHException;

    Survey createSurveySync(SurveyDTO dto, String tenant, String publisher) throws CHException;

    SurveyRes details(String tenant, String id) throws CHException;
    List<EngineScoreRes> surveyEngineScore(String tenant, String id) throws CHException;
    //List<SurveyQuestionScore> surveyQuestionScore(String tenant, String id);
    void deleteSurvey(String tenant, String id) throws CHException;
    void editSurvey(SurveyDTO survey, String tenant, String id) throws CHException;
    void updateStatus(String tenant, String id, String newStatus) throws CHException;

    ParticipantRes participantDetailsByEmail(String tenant, String id, String email) throws CHException;

    ParticipantRes participantDetails(String tenant, String id, String accessCode) throws CHException;

    List<StaticSurveyQuestion> surveyQuestions(String tenant, String surveyId, String accessCode) throws CHException;

    void deleteAll();
}
