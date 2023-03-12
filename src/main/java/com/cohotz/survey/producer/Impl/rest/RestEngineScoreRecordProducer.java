package com.cohotz.survey.producer.Impl.rest;

import com.cohotz.survey.client.api.EngineScoreRecordService;
import com.cohotz.survey.client.record.model.*;
import com.cohotz.survey.error.ServiceCHError;
import com.cohotz.survey.producer.EngineScoreRecordProducer;
import lombok.extern.slf4j.Slf4j;
import org.cohotz.boot.error.CHException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RestEngineScoreRecordProducer implements EngineScoreRecordProducer {

    @Autowired
    EngineScoreRecordService service;

    @Override
    @Async
    public void send(String key, com.cohotz.survey.dto.producer.enginescore.EngineScoreRecordDTO record) throws CHException {
        service.createEngineScoreRecord(format(record));
    }

    private EngineScoreRecordDTO format(com.cohotz.survey.dto.producer.enginescore.EngineScoreRecordDTO recordDTO) {
        MetadataDTO metadataDTO = new MetadataDTO().source(recordDTO.getMetadata().getSource()).traceId(recordDTO.getMetadata().getTraceId());
        EngineScoreDTO engineScoreDTO = new EngineScoreDTO()
                .email(recordDTO.getData().getEmail())
                .tenant(recordDTO.getData().getTenant())
                .reportingTo(recordDTO.getData().getReportingTo())
                .reportingHierarchy(recordDTO.getData().getReportingHierarchy())
                .engine(new CohotzEntity()
                        .code(recordDTO.getData().getEngine().getCode())
                        .name(recordDTO.getData().getEngine().getName()))
                .block(new CohotzEntity()
                        .code(recordDTO.getData().getBlock().getCode())
                        .name(recordDTO.getData().getBlock().getName()))
                .score(recordDTO.getData().getScore())
                .max(recordDTO.getData().getMax())
                .createdTs(OffsetDateTime.now(ZoneOffset.UTC))
                .cohorts(recordDTO.getData().getCohorts()
                        .stream()
                        .map(c -> new Cohort().name(c.getName()).value(c.getValue()))
                        .collect(Collectors.toList()));
        EngineScoreRecordDTO dto = new EngineScoreRecordDTO().metadata(metadataDTO).data(engineScoreDTO);
        return dto;
    }
}
