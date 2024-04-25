package com.cohotz.survey.model.mail.sib;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SMTPPeterRequest {
    private String from;
    private List<String> to;
    private String subject;
    private String html;
}
