package com.cohotz.survey.manager.impl;

import com.cohotz.survey.client.core.model.ChoiceBasedQuestion;
import com.cohotz.survey.client.core.model.PoolQuestion;
import com.cohotz.survey.client.core.model.Question;
import com.cohotz.survey.client.core.model.ResponseOption;
import com.cohotz.survey.dto.request.ChoiceBasedResponseDTO;
import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.engine.score.record.*;
import com.cohotz.survey.kafka.ResponseInsightProducer;
import com.cohotz.survey.manager.QuestionManager;
import com.cohotz.survey.model.Participant;
import com.cohotz.survey.model.question.ChoiceBasedSurveyQuestion;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.model.response.ChoiceResponse;
import com.cohotz.survey.model.response.Response;
import com.cohotz.survey.response.insight.record.QuestionDetails;
import com.cohotz.survey.response.insight.record.ResponseInsightRecord;
import com.cohotz.survey.score.record.EngineScoreRecordPublisher;
import lombok.extern.slf4j.Slf4j;
import org.cohotz.boot.error.CHException;
import org.cohotz.boot.model.common.CohotzEntity;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.cohotz.survey.SurveyConstants.SURVEY_SERVICE_CLIENT;
import static com.cohotz.survey.error.ServiceCHError.CULTR_SURVEY_MISSING_MANDATORY_RESPONSE;
import static org.cohotz.boot.CHConstants.LOG_TRACE_ID;

@Slf4j
@Component
public class ChoiceQuestionManager implements QuestionManager {

    @Autowired
    EngineScoreRecordPublisher engineScoreRecordPublisher;

    @Autowired
    ResponseInsightProducer responseInsightProducer;

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
        ChoiceResponse response = new ChoiceResponse();
        response.setQuestionCode(r.getQuestionCode());
        response.setSkipped(r.isSkipped());
        response.setComment(r.getComment());
        AtomicReference<Double> score = new AtomicReference<>((double) 0);
        ChoiceBasedSurveyQuestion ques = (ChoiceBasedSurveyQuestion) question;
        response.setSelections(responseDTO.getSelections());
        response.setChannel(r.getChannel());
        response.setMax(100);

        responseDTO.getSelections().forEach(s -> {
            log.debug("ques.getResponseOptionMap() {} and selection key {}", ques.getResponseOptionMap(), s);
            ResponseOption responseOption = ques.getResponseOptionMap().get(s - 1);
            //if (responseOption == null) invalidResponse.set(true);
            score.set(score.get() + responseOption.getScore());
            publishInsights(participant, question, responseOption);
        });
        response.setScore((score.get()*100)/ques.getMax());

        createEngineRecord(participant, question.getEngine(), response.getScore(), response.getMax());

        return response;
    }

    private void publishInsights(Participant participant, StaticSurveyQuestion ques, ResponseOption response) {
        log.info("Publishing Insight for [{}] from [{}] for question [{}]",
                participant.getEmail(), participant.getTenant(), ques.getPoolQuesReferenceCode());
        try {
            responseInsightProducer.publish(
                    participant.getId() + "__" + ques.getPoolQuesReferenceCode(),
                    ResponseInsightRecord.newBuilder()
                            .setMetadata(com.cohotz.survey.response.insight.record.MetaData.newBuilder()
                                    .setSource(SURVEY_SERVICE_CLIENT)
                                    .setTraceId(MDC.get(LOG_TRACE_ID))
                                    .build())
                            .setData(com.cohotz.survey.response.insight.record.ResponseInsight.newBuilder()
                                    .setEmail(participant.getEmail())
                                    .setTenant(participant.getTenant())
                                    .setQuestion(new QuestionDetails(ques.getPoolQuesReferenceCode(), ques.getText()))
                                    .setEngine(com.cohotz.survey.response.insight.record.EngineDetails.newBuilder()
                                            .setCode(ques.getEngine().getCode())
                                            .setName(ques.getEngine().getName())
                                            .build())
                                    .setBlock(com.cohotz.survey.response.insight.record.BlockDetails.newBuilder()
                                            .setCode(participant.getBlock().getCode())
                                            .setName(participant.getBlock().getName())
                                            .build())
                                    .setInsight(response.getInsight().getInsight())
                                    .setSource("STATIC_SURVEY")
                                    .setChannel("WEB")
                                    .build())
                            .build()
            );
        } catch (Exception e) {
            log.error("Error while publishing insight for [{}] from [{}] for question [{}]: [{}]",
                    participant.getEmail(), participant.getTenant(), ques.getPoolQuesReferenceCode(), e.getMessage());
        }
    }

    private void createEngineRecord(Participant participant, CohotzEntity engine, double score, double max) {
        log.debug("Creating Engine Score record for [{}] for engine [{}]", participant.getEmail(), engine.getCode());
        engineScoreRecordPublisher.publish(
                participant.getId()+"__"+engine.getCode(),
                EngineScoreRecord.newBuilder()
                        .setMetadata(MetaData.newBuilder()
                                .setSource(SURVEY_SERVICE_CLIENT)
                                .setTraceId(MDC.get(LOG_TRACE_ID))
                                .build())
                        .setData(EngineScore.newBuilder()
                                .setEmail(participant.getEmail())
                                .setReportingTo(participant.getReportingTo())
                                .setTenant(participant.getTenant())
                                .setEngine(new EngineDetails(engine.getCode(), engine.getName()))
                                .setBlock(new BlockDetails(participant.getBlock().getCode(), participant.getBlock().getName()))
                                .setSource("SURVEY")
                                .setScore(score)
                                .setMax(max)
                                .setCohorts(participant.getCohorts().stream().map(c -> new Cohort(c.getValue(), c.getName())).collect(Collectors.toList()))
                                .setReportingHierarchy(
                                        participant.getReportingHierarchy().stream().collect(Collectors.toList()))
                                .build()
                        ).build());
    }

//    @Override
//    public PoolQuestion createPoolQuestion(ApiResponsePoolQuestionResult result) {
//        PoolQuestion question = new ChoiceBasedQuestion();
//        BeanUtils.copyProperties(result, question);
//        return question;
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
