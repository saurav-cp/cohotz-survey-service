package com.cohotz.survey.score.record.impl;

import com.cohotz.survey.engine.score.record.EngineScoreRecord;
import com.cohotz.survey.score.record.EngineScoreRecordPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.cohotz.survey.SurveyConstants.ENGINE_SCORE_RECORD_KAFKA_TEMPLATE;
import static com.cohotz.survey.SurveyConstants.ENGINE_SCORE_RECORD_TOPIC;

@Component
public class EngineScoreKafkaProducer implements EngineScoreRecordPublisher {

    @Autowired
    @Qualifier(ENGINE_SCORE_RECORD_KAFKA_TEMPLATE)
    private KafkaTemplate<String, EngineScoreRecord> kafkaTemplate;

    @Override
    public void publish(String key, EngineScoreRecord message) {
        kafkaTemplate.send(ENGINE_SCORE_RECORD_TOPIC, key, message);
    }
}
