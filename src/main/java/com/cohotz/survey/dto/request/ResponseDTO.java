package com.cohotz.survey.dto.request;

import com.cohotz.survey.client.core.model.PoolQuestion;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChoiceBasedResponseDTO.class, name = "SINGLE_SELECT"),
        @JsonSubTypes.Type(value = ChoiceBasedResponseDTO.class, name = "MULTI_SELECT"),
        @JsonSubTypes.Type(value = TextBasedResponseDTO.class, name = "TEXT")
})
public class ResponseDTO {
    protected String questionCode;
    protected String comment;
    protected boolean skipped;
    protected String type;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseDTO that = (ResponseDTO) o;
        return questionCode.equals(that.questionCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionCode);
    }
}
