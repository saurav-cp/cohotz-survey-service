package com.cohotz.survey.dto.request.azai;

import com.cohotz.survey.model.azai.survey.QuestionTree;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutoTreeSurveyReq {
    private String name;
    private String description;
    private String engineCode;
    private String category;
    private QuestionTree questionTree;
}
