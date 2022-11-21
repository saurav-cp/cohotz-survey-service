package com.cohotz.survey.producer.Impl.kafka;

import com.cohotz.profile.preference.CultureEnginePreference;
import com.cohotz.profile.preference.CultureEnginePreferenceRecord;
import com.cohotz.profile.preference.MetaData;
import com.cohotz.profile.preference.PreferredEngine;
import com.cohotz.survey.dto.producer.preferredEngine.CultureEnginePreferenceDTO;
import com.cohotz.survey.dto.producer.preferredEngine.CultureEnginePreferenceRecordDTO;
import com.cohotz.survey.dto.producer.responseInsight.ResponseInsightRecordDTO;
import com.cohotz.survey.producer.EnginePreferenceProducer;
import com.cohotz.survey.response.insight.record.ResponseInsightRecord;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.cohotz.survey.SurveyConstants.PROFILE_ENGINE_PREF_RECORD_KAFKA_TEMPLATE;
import static com.cohotz.survey.SurveyConstants.SURVEY_SERVICE_CLIENT;
import static org.cohotz.boot.CHConstants.LOG_TRACE_ID;

@Component
@ConditionalOnProperty(prefix = "kafka", name = "enabled", havingValue = "true")
public class KafkaEnginePreferenceProducer implements EnginePreferenceProducer {

    @Autowired
    @Qualifier(PROFILE_ENGINE_PREF_RECORD_KAFKA_TEMPLATE)
    private KafkaTemplate<String, CultureEnginePreferenceRecord> kafkaTemplate;

    @Override
    @Async
    public void send(String key, CultureEnginePreferenceRecordDTO record) {
        kafkaTemplate.send(key, avroFormat(record));
    }

    private CultureEnginePreferenceRecord avroFormat(CultureEnginePreferenceRecordDTO record) {
        return CultureEnginePreferenceRecord.newBuilder()
                .setData(CultureEnginePreference.newBuilder()
                        .setEmail(record.getData().getEmail())
                        .setTenant(record.getData().getTenant())
                        .setEngines(record.getData().getEngines()
                                .stream()
                                .map(e -> new PreferredEngine(e.getName(), e.getCode()))
                                .collect(Collectors.toList()))
                        .build())
                .setMetadata(MetaData.newBuilder()
                        .setSource(SURVEY_SERVICE_CLIENT)
                        .setTraceId(MDC.get(LOG_TRACE_ID))
                        .build()).build();
    }
}
