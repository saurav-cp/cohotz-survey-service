package com.cohotz.survey.model.question;

import com.cohotz.survey.client.core.model.TenantSurveyConfigRes;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class StaticSurveyQuestionRes {
    private List<StaticSurveyQuestion> questions;
    private TenantSurveyConfigRes tenant;
}
