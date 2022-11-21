package com.cohotz.survey.producer;

import com.cohotz.survey.dto.producer.enginescore.EngineScoreRecordDTO;
import org.cohotz.boot.error.CHException;

public interface EngineScoreRecordProducer {
    void send(String key, EngineScoreRecordDTO record) throws CHException;
}
