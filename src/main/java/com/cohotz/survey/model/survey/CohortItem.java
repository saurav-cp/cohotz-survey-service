package com.cohotz.survey.model.survey;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.cohotz.boot.model.common.CohortProcessor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@ToString
public class CohortItem {
    private int position;
    private String field;
    @JsonProperty("display_name")
    @Field("display_name")
    private String displayName;
    private CohortProcessor processor;
}
