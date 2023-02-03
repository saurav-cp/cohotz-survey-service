package com.cohotz.survey.model.azai.survey;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Document("ch_azai_tree_survey")
public class AutoTreeSurvey {
    @Id
    private String id;
    private String name;
    private String description;
    @Field("engine_code")
    private String engineCode;
    private String category;
    @Field("question_tree")
    private QuestionTree questionTree;
    @CreatedDate
    @Field("created_timestamp")
    private LocalDateTime createdTS;
    @LastModifiedDate
    @Field("last_modified_timestamp")
    private LocalDateTime lastModifiedTS;
}
