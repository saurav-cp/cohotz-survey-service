package com.cohotz.survey.manager;

import com.cohotz.survey.client.core.model.PoolQuestion;
import com.cohotz.survey.client.core.model.Question;
import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.model.Participant;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.model.response.Response;
import org.cohotz.boot.error.CHException;

public interface QuestionManager {
    void validate(ResponseDTO response) throws CHException;
    Response evaluate(ResponseDTO response, StaticSurveyQuestion question, Participant participant);
    StaticSurveyQuestion createSurveyQuestion(Question blockQuestion, PoolQuestion question);
    StaticSurveyQuestion copySurveyQuestion(StaticSurveyQuestion source);
    boolean validateResponse(ResponseDTO dto, StaticSurveyQuestion question);
}
