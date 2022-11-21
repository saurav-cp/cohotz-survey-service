package com.cohotz.survey.producer.Impl.kafka;

import com.cohotz.survey.dto.producer.responseInsight.ResponseInsightRecordDTO;
import com.cohotz.survey.producer.ResponseInsightProducer;
import com.cohotz.survey.response.insight.record.QuestionDetails;
import com.cohotz.survey.response.insight.record.ResponseInsightRecord;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;

import static com.cohotz.survey.SurveyConstants.*;
import static org.cohotz.boot.CHConstants.LOG_TRACE_ID;

@ConditionalOnProperty(prefix = "kafka", name = "enabled", havingValue = "true")
public class KafkaResponseInsightProducer implements ResponseInsightProducer {

    @Autowired
    @Qualifier(RESPONSE_INSIGHT_RECORD_KAFKA_TEMPLATE)
    private KafkaTemplate<String, ResponseInsightRecord> kafkaTemplate;

    @Override
    @Async
    public void send(String key, ResponseInsightRecordDTO record) {
        kafkaTemplate.send(RESPONSE_INSIGHT_RECORD_TOPIC, key, avroFormat(record));
    }

    private ResponseInsightRecord avroFormat(ResponseInsightRecordDTO record) {
        return ResponseInsightRecord.newBuilder()
                .setMetadata(com.cohotz.survey.response.insight.record.MetaData.newBuilder()
                        .setSource(SURVEY_SERVICE_CLIENT)
                        .setTraceId(MDC.get(LOG_TRACE_ID))
                        .build())
                .setData(com.cohotz.survey.response.insight.record.ResponseInsight.newBuilder()
                        .setEmail(record.getData().getEmail())
                        .setTenant(record.getData().getTenant())
                        .setQuestion(new QuestionDetails(
                                record.getData().getQuestion().getCode(), record.getData().getQuestion().getName()))
                        .setEngine(com.cohotz.survey.response.insight.record.EngineDetails.newBuilder()
                                .setCode(record.getData().getEngine().getCode())
                                .setName(record.getData().getEngine().getName())
                                .build())
                        .setBlock(com.cohotz.survey.response.insight.record.BlockDetails.newBuilder()
                                .setCode(record.getData().getBlock().getCode())
                                .setName(record.getData().getBlock().getName())
                                .build())
                        .setInsight(record.getData().getInsight())
                        .setSource("STATIC_SURVEY")
                        .setChannel("WEB")
                        .build())
                .build();
    }
}
