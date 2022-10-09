package com.cohotz.survey.model.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nimbusds.oauth2.sdk.ResponseType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@ToString
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChoiceResponse.class, name = "SINGLE_SELECT"),
        @JsonSubTypes.Type(value = ChoiceResponse.class, name = "MULTI_SELECT"),
        @JsonSubTypes.Type(value = TextResponse.class, name = "TEXT")
})
public class Response {
    @Field("question_code")
    private String questionCode;
    private double score;
    private double max;
    private String comment;
    private List<String> keywords;
    private boolean skipped;
    @Field("smart_skip")
    private boolean smartSkip = false;
    protected ResponseType type;
}
