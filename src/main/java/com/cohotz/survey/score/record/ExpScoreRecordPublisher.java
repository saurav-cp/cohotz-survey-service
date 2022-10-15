package com.cohotz.survey.score.record;

import com.cohotz.survey.exp.score.record.ExperienceScoreRecord;

public interface ExpScoreRecordPublisher {
    void publish(String key, ExperienceScoreRecord message);
}
