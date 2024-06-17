package com.cohotz.survey.service;

import com.cohotz.survey.dto.email.ParticipantNotificationDetails;
import com.cohotz.survey.model.Survey;

import java.util.List;

public interface NotificationService {
    void sendSurvey(Survey survey, ParticipantNotificationDetails details);
    void sendEngagementSurvey(Survey survey, List<ParticipantNotificationDetails> participants);
}
