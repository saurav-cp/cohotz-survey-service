package com.cohotz.survey.score.record;

import com.cohotz.survey.engine.score.record.EngineScoreRecord;

public interface EngineScoreRecordPublisher {
    void publish(String key, EngineScoreRecord message);
}
