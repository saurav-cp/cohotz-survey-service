package com.cohotz.survey.dto.response;

import com.cohotz.survey.model.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ParticipantRes {

    public ParticipantRes(){
        this.responses = new ArrayList<>();
    }
    private String surveyName;
    private String publisher;
    private String accessCode;
    private String email;
    private boolean emailSent;
    private boolean surveyComplete;
    private List<Response> responses;
    private String link;
}
