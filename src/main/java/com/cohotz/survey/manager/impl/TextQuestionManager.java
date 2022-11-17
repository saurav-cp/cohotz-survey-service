package com.cohotz.survey.manager.impl;

import com.cohotz.survey.client.core.model.ApiResponsePoolQuestionResult;
import com.cohotz.survey.client.core.model.PoolQuestion;
import com.cohotz.survey.client.core.model.Question;
import com.cohotz.survey.client.core.model.TextResponseBasedQuestion;
import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.dto.request.TextBasedResponseDTO;
import com.cohotz.survey.manager.QuestionManager;
import com.cohotz.survey.model.Participant;
import com.cohotz.survey.model.question.ChoiceBasedSurveyQuestion;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.model.question.TextResponseBasedSurveyQuestion;
import com.cohotz.survey.model.response.Response;
import com.cohotz.survey.model.response.TextResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cohotz.boot.model.common.CohotzEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component("TEXT")
public class TextQuestionManager implements QuestionManager {
    @Override
    public void validate(ResponseDTO response) {
        //Add validation
    }

    @Override
    public Response evaluate(ResponseDTO r, StaticSurveyQuestion question, Participant participant) {
        TextResponse response = new TextResponse();
        TextBasedResponseDTO responseDTO = (TextBasedResponseDTO)r;
        response.setQuestionCode(r.getQuestionCode());
        response.setSkipped(r.isSkipped());
        response.setComment(r.getComment());
        response.setScore(100);
        response.setResponse(responseDTO.getResponse());
        return response;
    }

//    @Override
//    public PoolQuestion createPoolQuestion(ApiResponsePoolQuestionResult result) {
//        PoolQuestion question = new TextResponseBasedQuestion();
//        BeanUtils.copyProperties(result, question);
//        return question;
//    }

    @Override
    public StaticSurveyQuestion createSurveyQuestion(Question blockQuestion, PoolQuestion question) {
        TextResponseBasedQuestion poolQuestion = (TextResponseBasedQuestion) question;
        log.debug("Creating question from code {}", blockQuestion.getPoolQuesReferenceCode());
        ChoiceBasedSurveyQuestion sQuestion = new ChoiceBasedSurveyQuestion();
        BeanUtils.copyProperties(blockQuestion, sQuestion);

        sQuestion.setId(poolQuestion.getId());
        sQuestion.setEngine(new CohotzEntity(poolQuestion.getEngine().getName(), poolQuestion.getEngine().getCode()));
        sQuestion.setText(poolQuestion.getText());
        sQuestion.setResponseType(poolQuestion.getResponseType());

        return sQuestion;
    }

    @Override
    public StaticSurveyQuestion copySurveyQuestion(StaticSurveyQuestion source) {
        StaticSurveyQuestion question = new TextResponseBasedSurveyQuestion();
        BeanUtils.copyProperties(source, question);
        return question;
    }

    @Override
    public boolean validateResponse(ResponseDTO dto, StaticSurveyQuestion question) {
        TextBasedResponseDTO responseDTO = (TextBasedResponseDTO)dto;
        if(StringUtils.isBlank(responseDTO.getResponse())) return false;
        return true;
    }
}
