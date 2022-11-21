package com.cohotz.survey.producer.Impl.kafka;

import com.cohotz.survey.dto.producer.enginescore.EngineScoreRecordDTO;
import com.cohotz.survey.engine.score.record.*;
import com.cohotz.survey.producer.EngineScoreRecordProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.cohotz.survey.SurveyConstants.ENGINE_SCORE_RECORD_KAFKA_TEMPLATE;
import static com.cohotz.survey.SurveyConstants.ENGINE_SCORE_RECORD_TOPIC;

@Component
@ConditionalOnProperty(prefix = "kafka", name = "enabled", havingValue = "true")
public class KafkaEngineScoreRecordProducer implements EngineScoreRecordProducer {

    @Autowired
    @Qualifier(ENGINE_SCORE_RECORD_KAFKA_TEMPLATE)
    private KafkaTemplate<String, EngineScoreRecord> kafkaTemplate;

    @Override
    @Async
    public void send(String key, EngineScoreRecordDTO record) {
        kafkaTemplate.send(ENGINE_SCORE_RECORD_TOPIC, key, avroFormat(record));
    }

    private EngineScoreRecord avroFormat(EngineScoreRecordDTO record) {
        return EngineScoreRecord.newBuilder()
                .setMetadata(MetaData.newBuilder()
                        .setSource(record.getMetadata().getSource())
                        .setTraceId(record.getMetadata().getTraceId())
                        .build())
                .setData(EngineScore.newBuilder()
                        .setEmail(record.getData().getEmail())
                        .setReportingTo(record.getData().getReportingTo())
                        .setTenant(record.getData().getTenant())
                        .setEngine(new EngineDetails(
                                record.getData().getEngine().getCode(), record.getData().getEngine().getName()))
                        .setBlock(new BlockDetails(
                                record.getData().getBlock().getCode(), record.getData().getBlock().getName()))
                        .setSource(record.getData().getSource())
                        .setScore(record.getData().getScore())
                        .setMax(record.getData().getMax())
                        .setCohorts(record.getData()
                                .getCohorts()
                                .stream()
                                .map(c -> new Cohort(c.getValue(), c.getName())).collect(Collectors.toList()))
                        .setReportingHierarchy(
                                record.getData().getReportingHierarchy().stream().collect(Collectors.toList()))
                        .build()
                ).build();
    }
}
