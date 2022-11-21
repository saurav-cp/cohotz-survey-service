package com.cohotz.survey.producer.Impl.rest;


import com.cohotz.survey.client.api.EngineScoreRecordService;
import com.cohotz.survey.client.record.model.CohotzEntity;
import com.cohotz.survey.client.record.model.MetadataDTO;
import com.cohotz.survey.client.record.model.ResponseInsightDTO;
import com.cohotz.survey.client.record.model.ResponseInsightRecordDTO;
import com.cohotz.survey.producer.ResponseInsightProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RestResponseInsightProducer implements ResponseInsightProducer {

    @Autowired
    EngineScoreRecordService service;

    @Override
    @Async
    public void send(String key, com.cohotz.survey.dto.producer.responseInsight.ResponseInsightRecordDTO record) {
        try {
            service.createResponseInsightRecord(format(record));
        } catch (Exception e) {
            log.error("Exception while creating response insight: [{}]", e.getMessage());
        }
    }

    private ResponseInsightRecordDTO format(com.cohotz.survey.dto.producer.responseInsight.ResponseInsightRecordDTO recordDTO) {
        MetadataDTO metadataDTO = new MetadataDTO().source(recordDTO.getMetadata().getSource()).traceId(recordDTO.getMetadata().getTraceId());
        ResponseInsightDTO engineScoreDTO = new ResponseInsightDTO()
                .email(recordDTO.getData().getEmail())
                .tenant(recordDTO.getData().getTenant())
                .question(new CohotzEntity()
                        .code(recordDTO.getData().getQuestion().getCode())
                        .name(recordDTO.getData().getQuestion().getName()))
                .engine(new CohotzEntity()
                        .code(recordDTO.getData().getEngine().getCode())
                        .name(recordDTO.getData().getEngine().getName()))
                .block(new CohotzEntity()
                        .code(recordDTO.getData().getBlock().getCode())
                        .name(recordDTO.getData().getBlock().getName()))
                .insight(recordDTO.getData().getInsight());
        ResponseInsightRecordDTO dto = new ResponseInsightRecordDTO().metadata(metadataDTO).data(engineScoreDTO);
        return dto;
    }
}
