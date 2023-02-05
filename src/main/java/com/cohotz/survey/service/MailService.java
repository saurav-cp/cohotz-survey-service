package com.cohotz.survey.service;

import com.cohotz.survey.dto.email.ParticipantEmail;
import com.cohotz.survey.model.Survey;

import java.util.List;
import java.util.Map;

public interface MailService {
    //void registrationConfirmation(String email, String tenant);
    //void resetPasswordLink(String email, String tenant, ResetPassword resetPassword);
    //void passwordResetConfirmation(String email, String tenant);
    //void sendGeneratedPassword(String email, String tenant, GeneratePasswordRes res);

    //void profileUpdateConfirmation(String email, String tenant);
    void sendSurvey(Survey survey, String email, String name, String link);
    void sendEngagementSurvey(Survey survey, List<ParticipantEmail> participants);
    //SIBSendEmailRes send(SIBSendEmailReq req);
}
