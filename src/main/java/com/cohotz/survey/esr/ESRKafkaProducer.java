package com.cohotz.survey.esr;

import com.cohotz.survey.esr.avro.EngineScoreRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.cohotz.survey.SurveyConstants.ENGINE_SCORE_RECORD_TOPIC;

@Component
public class ESRKafkaProducer implements ESRRecordPublisher{

    @Autowired
    private KafkaTemplate<String, EngineScoreRecord> kafkaTemplate;

    @Override
    public void publish(EngineScoreRecord message) {
        kafkaTemplate.send(ENGINE_SCORE_RECORD_TOPIC, message);
    }

    @Override
    public void publish(String key, EngineScoreRecord message) {
        kafkaTemplate.send(ENGINE_SCORE_RECORD_TOPIC, key, message);
    }
}
