package com.cohotz.survey.producer;

import com.cohotz.survey.dto.producer.preferredEngine.CultureEnginePreferenceRecordDTO;

public interface EnginePreferenceProducer {
    void send(String key, CultureEnginePreferenceRecordDTO record);
}
