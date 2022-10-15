package com.cohotz.survey.service;

import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.dto.response.ParticipantMinRes;
import com.cohotz.survey.model.Participant;
import com.cohotz.survey.model.Survey;
import com.cohotz.survey.model.SurveyStatus;
import org.cohotz.boot.error.CHException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SurveyParticipantService {
    void createParticipants(Survey survey) throws CHException;
    void createParticipants(Survey survey, Set<String> participants) throws CHException;
    Participant createParticipant(String email, Survey survey) throws CHException;

    Participant updateParticipant(Participant participant);
    void updateScoreAccFlag(String tenant, String surveyId, String participantId);

    List<Participant> findAllParticipantForSurvey(String tenant, String surveyId);
    List<Participant> findAllParticipantWithNewResponse(String tenant, String surveyId);
    long surveyParticipantCount(String tenant, String surveyId);
    long surveyResponseCount(String tenant, String surveyId);
    void emailParticipantForSurvey(String tenant, String surveyId, Survey survey);
    void updateStatus(String tenant, String surveyId, SurveyStatus status);
    List<Participant> findAllForEmployee(String tenant, String email, LocalDateTime responseTS);
    //List<AssignedSurvey> findAssignedSurveys(boolean pending);
    Participant findParticipantByEmailForSurvey(String surveyId, String tenant, String email);
    Participant findParticipantByAccessCodeForSurvey(String surveyId, String tenant, String accessCode);
    void updateReminder(String tenant, String surveyId, String accessCode) throws CHException;
    List<ParticipantMinRes> listParticipant(String tenant, String id) throws CHException;
    void addResponse(String tenant, String surveyId, String accessCode, List<ResponseDTO> responses) throws CHException;

    void deleteBySurveyId(String surveyId);
    void deleteAll();
}
