package com.cohotz.survey.dto.request.azai;

import com.cohotz.survey.model.azai.survey.AssignedSurvey;
import com.cohotz.survey.model.azai.survey.QuestionTree;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ConversationAction {
    private boolean conversation = true;
    private String sessionId;
    private QuestionTree questionTree;
    private List<AssignedSurvey> pendingAssignedSurveys;
}
