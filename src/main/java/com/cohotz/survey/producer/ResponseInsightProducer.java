package com.cohotz.survey.producer;

import com.cohotz.survey.dto.producer.responseInsight.ResponseInsightRecordDTO;
import org.cohotz.boot.error.CHException;

public interface ResponseInsightProducer {
    void send(String key, ResponseInsightRecordDTO record);
}
