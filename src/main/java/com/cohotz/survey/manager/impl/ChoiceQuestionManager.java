package com.cohotz.survey.manager.impl;

import com.cohotz.survey.client.core.model.*;
import com.cohotz.survey.dto.request.ChoiceBasedResponseDTO;
import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.manager.QuestionManager;
import com.cohotz.survey.model.question.ChoiceBasedSurveyQuestion;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.model.response.ChoiceResponse;
import com.cohotz.survey.model.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.cohotz.boot.error.CHException;
import org.springframework.beans.BeanUtils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static com.cohotz.survey.error.ServiceCHError.CULTR_SURVEY_MISSING_MANDATORY_RESPONSE;

@Slf4j
public class ChoiceQuestionManager implements QuestionManager {


    @Override
    public void validate(ResponseDTO response) throws CHException {
        ChoiceBasedResponseDTO res = (ChoiceBasedResponseDTO)response;
        if(res.getSelections() == null || res.getSelections().size() ==0) {
            throw  new CHException(CULTR_SURVEY_MISSING_MANDATORY_RESPONSE);
        }
    }

    @Override
    public Response evaluate(ResponseDTO r, StaticSurveyQuestion question) {
        ChoiceBasedResponseDTO responseDTO = (ChoiceBasedResponseDTO)r;
        ChoiceResponse response = new ChoiceResponse();
        response.setQuestionCode(r.getQuestionCode());
        response.setSkipped(r.isSkipped());
        response.setComment(r.getComment());
        AtomicReference<Double> score = new AtomicReference<>((double) 0);
        ChoiceBasedSurveyQuestion ques = (ChoiceBasedSurveyQuestion) question;
        response.setSelections(responseDTO.getSelections());
        response.setMax(ques.getMax());

        responseDTO.getSelections().forEach(s -> {
            log.debug("ques.getResponseOptionMap() {} and selection key {}", ques.getResponseOptionMap(), s);
            ResponseOption responseOption = ques.getResponseOptionMap().get(s - 1);
            //if (responseOption == null) invalidResponse.set(true);
            score.set(score.get() + responseOption.getScore());
        });
        response.setScore((score.get()*100)/ques.getMax());

        return response;
    }

    @Override
    public PoolQuestion createPoolQuestion(ApiResponsePoolQuestionResult result) {
        PoolQuestion question = new ChoiceBasedQuestion();
        BeanUtils.copyProperties(result, question);
        return question;
    }

    @Override
    public StaticSurveyQuestion createSurveyQuestion(Question blockQuestion, PoolQuestion question) {
        ChoiceBasedQuestion poolQuestion = (ChoiceBasedQuestion) question;
        log.debug("Creating question from code {}", blockQuestion.getPoolQuesReferenceCode());
        ChoiceBasedSurveyQuestion sQuestion = new ChoiceBasedSurveyQuestion();
        BeanUtils.copyProperties(blockQuestion, sQuestion);

        sQuestion.setId(poolQuestion.getId());
        sQuestion.setEngine(poolQuestion.getEngine());
        sQuestion.setText(poolQuestion.getText());
        sQuestion.setResponseType(poolQuestion.getResponseType());
        poolQuestion.getResponseOptionMap().forEach((k,ro) -> {
            sQuestion.getResponseOptionMap().put(Integer.parseInt(k), ro);
        });
        //sQuestion.setResponseOptionMap(poolQuestion.getResponseOptionMap());
        sQuestion.setMax(poolQuestion.getMax());

        return sQuestion;
    }

    @Override
    public boolean validateResponse(ResponseDTO dto, StaticSurveyQuestion question) {
        AtomicBoolean invalidResponse = new AtomicBoolean(false);
        ChoiceBasedResponseDTO responseDTO = (ChoiceBasedResponseDTO)dto;
        ChoiceBasedSurveyQuestion ques = (ChoiceBasedSurveyQuestion) question;
        responseDTO.getSelections().forEach(s -> {
            log.debug("Question {} and selection {}", ques, s);
            ResponseOption responseOption = ques.getResponseOptionMap().get(s - 1);
            if (responseOption == null) invalidResponse.set(true);
        });

        return invalidResponse.get();
    }
}
