package com.cohotz.survey.service;

import com.cohotz.survey.client.core.model.EngineWeight;
import com.cohotz.survey.client.core.model.Question;
import com.cohotz.survey.dto.request.SurveyDTO;
import com.cohotz.survey.model.Participant;
import com.cohotz.survey.model.Survey;
import com.cohotz.survey.model.SurveyStatus;
import org.cohotz.boot.error.CHException;

import java.time.LocalDateTime;
import java.util.List;

public interface SurveyParticipantService {
    void createParticipantsSync(String surveyId, String tenant, SurveyDTO surveyDto, List<Question> questions, List<EngineWeight> engines) throws CHException;
    void createParticipants(String surveyId, String tenant, SurveyDTO surveyDto, List<Question> questions, List<EngineWeight> engines) throws CHException;
    Participant createParticipant(String tenant, String surveyId, SurveyDTO surveyDto, String email, List<Question> questions, List<EngineWeight> engines) throws CHException;

    Participant updateParticipant(Participant participant);
    Participant updateScoreAccFlag(String participantId);

    List<Participant> findAllParticipantForSurvey(String tenant, String surveyId);
    List<Participant> findAllParticipantWithNewResponse(String tenant, String surveyId);
    long surveyParticipantCount(String tenant, String surveyId);
    long surveyResponseCount(String tenant, String surveyId);
    void emailParticipantForSurvey(String tenant, String surveyId, Survey survey);
    void updateSurveyStatus(String tenant, String surveyId, SurveyStatus status);
    List<Participant> findAllForEmployee(String tenant, String email, LocalDateTime responseTS);
    //List<AssignedSurvey> findAssignedSurveys(boolean pending);
    Participant findParticipantByEmailForSurvey(String surveyId, String tenant, String email);
    Participant findParticipantByAccessCodeForSurvey(String surveyId, String tenant, String accessCode);
    void updateReminder(String tenant, String surveyId, String accessCode) throws CHException;
    //double participation(String tenant, String surveyId);

    void deleteBySurveyId(String surveyId);
    void deleteAll();
}
