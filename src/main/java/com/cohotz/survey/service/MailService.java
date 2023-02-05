package com.cohotz.survey.service;

import com.cohotz.survey.model.Survey;

public interface MailService {
    //void registrationConfirmation(String email, String tenant);
    //void resetPasswordLink(String email, String tenant, ResetPassword resetPassword);
    //void passwordResetConfirmation(String email, String tenant);
    //void sendGeneratedPassword(String email, String tenant, GeneratePasswordRes res);

    //void profileUpdateConfirmation(String email, String tenant);
    void sendSurvey(Survey survey, String email, String link);
    void sendEngagementSurvey(Survey survey, String email, String link);
    //SIBSendEmailRes send(SIBSendEmailReq req);
}
