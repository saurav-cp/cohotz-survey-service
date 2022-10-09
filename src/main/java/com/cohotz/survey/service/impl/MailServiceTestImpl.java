package com.cohotz.survey.service.impl;

import com.cohotz.survey.model.Survey;
import com.cohotz.survey.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
//@Profile({"it", "test", "perf", "docker"})
public class MailServiceTestImpl implements MailService {

//    @Async
//    @Override
//    public void registrationConfirmation(String email, String tenant) {
//        log.warn("Sending email for successful registration of {} for {}", email, tenant);
//    }
//
//    @Async
//    @Override
//    public void resetPasswordLink(String email, String tenant, ResetPassword resetPassword) {
//        log.warn("Sending email for reset password link  {} for {}", email, tenant);
//    }
//
//    @Async
//    @Override
//    public void passwordResetConfirmation(String email, String tenant) {
//        log.warn("Sending email for reset password confirmation  {} for {}", email, tenant);
//    }

//    @Override
//    public void sendGeneratedPassword(String email, String tenant, GeneratePasswordRes res) {
//        log.warn("TO BE IMPLEMENTED: sending email for newly generated password for  {} for {}", email, tenant);
//    }

//    @Async
//    @Override
//    public void profileUpdateConfirmation(String email, String tenant) {
//        log.warn("Sending email for profile update confirmation  {} for {}", email, tenant);
//    }

    @Async
    @Override
    public void sendSurvey(Survey survey, String email, String link) {
        log.warn("Sending email for survey  {} for participant {}", survey.getId(), email);
    }

//    @Override
//    public SIBSendEmailRes send(SIBSendEmailReq req) {
//        return new SIBSendEmailRes();
//    }
}
