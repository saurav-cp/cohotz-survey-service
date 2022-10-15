package com.cohotz.survey.score.record.impl;

import com.cohotz.survey.exp.score.record.ExperienceScoreRecord;
import com.cohotz.survey.score.record.ExpScoreRecordPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.cohotz.survey.SurveyConstants.*;

@Component
public class ExpScoreKafkaProducer implements ExpScoreRecordPublisher {

    @Autowired
    @Qualifier(EXP_SCORE_RECORD_KAFKA_TEMPLATE)
    private KafkaTemplate<String, ExperienceScoreRecord> kafkaTemplate;

    @Override
    public void publish(String key, ExperienceScoreRecord message) {
        kafkaTemplate.send(EXP_SCORE_RECORD_TOPIC, key, message);
    }
}
