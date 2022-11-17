package com.cohotz.survey.kafka;

import com.cohotz.survey.response.insight.record.ResponseInsightRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.cohotz.survey.SurveyConstants.RESPONSE_INSIGHT_RECORD_KAFKA_TEMPLATE;
import static com.cohotz.survey.SurveyConstants.RESPONSE_INSIGHT_RECORD_TOPIC;

@Component
@Slf4j
public class ResponseInsightProducer {

    @Autowired
    @Qualifier(RESPONSE_INSIGHT_RECORD_KAFKA_TEMPLATE)
    private KafkaTemplate<String, ResponseInsightRecord> kafkaTemplate;

    public void publish(String key, ResponseInsightRecord message) {
        kafkaTemplate.send(RESPONSE_INSIGHT_RECORD_TOPIC, key, message);
    }
}
