package com.cohotz.survey.service;

import com.cohotz.survey.dto.email.ParticipantNotificationDetails;
import com.cohotz.survey.model.Survey;

import java.util.List;

public interface MailService {
    //void registrationConfirmation(String email, String tenant);
    //void resetPasswordLink(String email, String tenant, ResetPassword resetPassword);
    //void passwordResetConfirmation(String email, String tenant);
    //void sendGeneratedPassword(String email, String tenant, GeneratePasswordRes res);

    //void profileUpdateConfirmation(String email, String tenant);
    void sendSurvey(Survey survey, String email, String name, String link);
    void sendEngagementSurvey(Survey survey, List<ParticipantNotificationDetails> participants);
    //SIBSendEmailRes send(SIBSendEmailReq req);
}
