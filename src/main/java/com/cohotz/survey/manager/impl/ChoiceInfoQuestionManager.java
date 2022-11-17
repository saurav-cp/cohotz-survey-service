package com.cohotz.survey.manager.impl;

import com.cohotz.profile.preference.CultureEnginePreference;
import com.cohotz.profile.preference.CultureEnginePreferenceRecord;
import com.cohotz.profile.preference.MetaData;
import com.cohotz.profile.preference.PreferredEngine;
import com.cohotz.survey.client.core.model.ChoiceBasedQuestion;
import com.cohotz.survey.client.core.model.PoolQuestion;
import com.cohotz.survey.client.core.model.Question;
import com.cohotz.survey.client.core.model.ResponseOption;
import com.cohotz.survey.dto.request.ChoiceBasedResponseDTO;
import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.kafka.EnginePreferenceProducer;
import com.cohotz.survey.kafka.ResponseInsightProducer;
import com.cohotz.survey.manager.QuestionManager;
import com.cohotz.survey.model.Participant;
import com.cohotz.survey.model.question.ChoiceBasedSurveyQuestion;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.model.response.ChoiceResponse;
import com.cohotz.survey.model.response.Response;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cohotz.boot.error.CHException;
import org.cohotz.boot.model.common.CohotzEntity;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.cohotz.survey.SurveyConstants.SURVEY_SERVICE_CLIENT;
import static com.cohotz.survey.error.ServiceCHError.CULTR_SURVEY_MISSING_MANDATORY_RESPONSE;
import static org.cohotz.boot.CHConstants.LOG_TRACE_ID;

@Slf4j
@NoArgsConstructor
public class ChoiceInfoQuestionManager implements QuestionManager {

    private EnginePreferenceProducer producer;

    private ResponseInsightProducer responseInsightProducer;

    public ChoiceInfoQuestionManager(EnginePreferenceProducer producer) {
        this.producer = producer;
    }

    @Override
    public void validate(ResponseDTO response) throws CHException {
        ChoiceBasedResponseDTO res = (ChoiceBasedResponseDTO)response;
        if(res.getSelections() == null || res.getSelections().size() ==0) {
            throw  new CHException(CULTR_SURVEY_MISSING_MANDATORY_RESPONSE);
        }
    }

    @Override
    public Response evaluate(ResponseDTO r, StaticSurveyQuestion question, Participant participant) {
        ChoiceBasedResponseDTO responseDTO = (ChoiceBasedResponseDTO)r;
        ChoiceBasedSurveyQuestion ques = (ChoiceBasedSurveyQuestion) question;
        List<PreferredEngine> engines = new ArrayList<>();
        responseDTO.getSelections().forEach(s -> {
            log.debug("ques.getResponseOptionMap() {} and selection key {}", ques.getResponseOptionMap(), s);
            ResponseOption responseOption = ques.getResponseOptionMap().get(s - 1);
            String engine = responseOption.getText();
            engines.add(new PreferredEngine(engine, engine));
        });

        CultureEnginePreferenceRecord record = CultureEnginePreferenceRecord.newBuilder()
                .setData(CultureEnginePreference.newBuilder()
                        .setEmail(participant.getEmail())
                        .setTenant(participant.getTenant())
                        .setEngines(engines).build())
                .setMetadata(MetaData.newBuilder()
                        .setSource(SURVEY_SERVICE_CLIENT)
                        .setTraceId(MDC.get(LOG_TRACE_ID))
                        .build()).build();
        producer.publish(participant.getId(), record);

        //Just to capture the response.
        ChoiceResponse response = new ChoiceResponse();
        response.setQuestionCode(r.getQuestionCode());
        response.setSkipped(r.isSkipped());
        response.setComment(r.getComment());
        response.setChannel(r.getChannel());
        response.setScore(0);

        return response;
    }

//    @Override
//    @Async
//    public void profileUpdate(User user, ResponseDTO response, StaticSurveyQuestion question) {
//        ChoiceBasedResponseDTO responseDTO = (ChoiceBasedResponseDTO)response;
//        ChoiceBasedSurveyQuestion ques = (ChoiceBasedSurveyQuestion) question;
//        responseDTO.getSelections().forEach(s -> {
//            String name = ques.getResponseOptionMap().get(s - 1).getText();
//            user.getEngines().forEach(e -> {
//                if(e.getName().equalsIgnoreCase(name)){
//                    log.info("Setting engine [{}] as preferred for {}", name, user.getEmail());
//                    e.setPreferred(true);
//                }
//            });
//        });
//        userDao.save(user);
//    }


    @Override
    public StaticSurveyQuestion createSurveyQuestion(Question blockQuestion, PoolQuestion question) {
        ChoiceBasedQuestion poolQuestion = (ChoiceBasedQuestion) question;
        log.debug("Creating question from code {}", blockQuestion.getPoolQuesReferenceCode());
        ChoiceBasedSurveyQuestion sQuestion = new ChoiceBasedSurveyQuestion();
        BeanUtils.copyProperties(blockQuestion, sQuestion);

        sQuestion.setId(poolQuestion.getId());
        sQuestion.setEngine(new CohotzEntity(poolQuestion.getEngine().getName(), poolQuestion.getEngine().getCode()));
        sQuestion.setText(poolQuestion.getText());
        sQuestion.setResponseType(poolQuestion.getResponseType());
        poolQuestion.getResponseOptionMap().forEach((k,ro) -> {
            sQuestion.getResponseOptionMap().put(Integer.parseInt(k), ro);
        });
        sQuestion.setMax(poolQuestion.getMax());

        return sQuestion;
    }

    @Override
    public StaticSurveyQuestion copySurveyQuestion(StaticSurveyQuestion source) {
        StaticSurveyQuestion question = new ChoiceBasedSurveyQuestion();
        BeanUtils.copyProperties(source, question);
        return question;
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
