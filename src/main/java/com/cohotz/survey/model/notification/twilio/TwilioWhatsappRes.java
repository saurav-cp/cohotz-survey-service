package com.cohotz.survey.model.notification.twilio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TwilioWhatsappRes {
    private String message_sid;
    private String status;
}
