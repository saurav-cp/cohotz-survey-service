package com.cohotz.survey.model.survey;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
public class CohortItem {
    private int position;
    private String field;
    @JsonProperty("display_name")
    @Field("display_name")
    private String displayName;
}
