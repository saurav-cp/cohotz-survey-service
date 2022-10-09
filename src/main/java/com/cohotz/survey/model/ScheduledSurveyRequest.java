package com.cohotz.survey.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cp_scheduled_survey")
@Getter
@Setter
public class ScheduledSurveyRequest {
    @Id
    private String id;
    private String tenant;
    private String publisher;
    private String participant;
    private String block;
    private Status status;

    public enum Status {
        PENDING, COMPLETE
    }
}
