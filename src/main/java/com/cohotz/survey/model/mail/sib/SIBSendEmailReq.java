package com.cohotz.survey.model.mail.sib;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SIBSendEmailReq {
    private SIBSender sender;
    private List<SIBTo> to;
    private String subject;
    private String htmlContent;
}
