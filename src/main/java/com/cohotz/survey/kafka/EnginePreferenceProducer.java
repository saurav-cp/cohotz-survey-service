package com.cohotz.survey.kafka;

import com.cohotz.profile.preference.CultureEnginePreferenceRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.cohotz.survey.SurveyConstants.PROFILE_ENGINE_PREF_RECORD_KAFKA_TEMPLATE;
import static com.cohotz.survey.SurveyConstants.PROFILE_ENGINE_PREF_RECORD_TOPIC;

@Component
@Slf4j
public class EnginePreferenceProducer {

    @Autowired
    @Qualifier(PROFILE_ENGINE_PREF_RECORD_KAFKA_TEMPLATE)
    private KafkaTemplate<String, CultureEnginePreferenceRecord> kafkaTemplate;

    public void publish(String key, CultureEnginePreferenceRecord message) {
        kafkaTemplate.send(PROFILE_ENGINE_PREF_RECORD_TOPIC, key, message);
    }
}
