package com.cohotz.survey.model.azai.session;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Event {
    private int eventId;
    private String initiator;
    private String prompt;
    private String response;
    private String sentiment;
    private double score;
    private List<String> keywords;
    private boolean end;
    private int level;
    private Integer previousEventId;
    private LocalDateTime createdOn;
}
