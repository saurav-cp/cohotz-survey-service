package com.cohotz.survey.dto.request.session;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Validated
public class EventReq {

    @NotNull(message = "Event Id cannot be null")
    private int eventId;

    @NotNull(message = "Initiator cannot be null")
    @NotBlank(message = "Initiator cannot be blank")
    private String initiator;

    @NotNull(message = "Prompt cannot be null")
    @NotBlank(message = "Prompt cannot be blank")
    private String prompt;

    @NotNull(message = "Response cannot be null")
    @NotBlank(message = "Response cannot be blank")
    private String response;

    @NotNull(message = "Sentiment cannot be null")
    @NotBlank(message = "Sentiment cannot be blank")
    private String sentiment;

    @NotNull(message = "Score of the response cannot be null")
    private double score;

    private List<String> keywords;

    private boolean end;

    @NotNull(message = "Level of the prompt cannot be null")
    private int level;

    private Integer previousEventId;

    @NotNull(message = "Created On Datetime cannot be null")
    private LocalDateTime createdOn;
}
