package com.cohotz.survey.esr;

import com.cohotz.survey.esr.avro.EngineScoreRecord;

public interface ESRRecordPublisher {
    void publish(EngineScoreRecord message);
    void publish(String key, EngineScoreRecord message);
}
