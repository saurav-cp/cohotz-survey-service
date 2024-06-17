package com.cohotz.survey.model.notification.twilio;

import com.cohotz.survey.model.mail.sib.SIBSender;
import com.cohotz.survey.model.mail.sib.SIBTo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TwilioWhatsappReq {
    private String to;
    private String message;
}
