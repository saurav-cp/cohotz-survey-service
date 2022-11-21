package com.cohotz.survey.producer.Impl.rest;

import com.cohotz.survey.client.api.UserService;
import com.cohotz.survey.client.profile.model.MetadataDTO;
import com.cohotz.survey.client.profile.model.PreferredEngineDTO;
import com.cohotz.survey.client.profile.model.PreferredEngineRecordDTO;
import com.cohotz.survey.client.profile.model.ProfileCultureEngine;
import com.cohotz.survey.dto.producer.preferredEngine.CultureEnginePreferenceRecordDTO;
import com.cohotz.survey.producer.EnginePreferenceProducer;
import lombok.extern.slf4j.Slf4j;
import org.cohotz.boot.error.CHException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.cohotz.survey.SurveyConstants.USER_API_BEAN;

@Component
@Slf4j
public class RestEnginePreferenceProducer implements EnginePreferenceProducer {

    @Autowired
    UserService userService;

    @Override
    @Async
    public void send(String key, CultureEnginePreferenceRecordDTO record) {
        try {
            userService.updatePreference(format(record));
        } catch (Exception e) {
            log.error("Exception while updating profile engine preference: [{}]", e.getMessage());
        }
    }

    private PreferredEngineRecordDTO format(CultureEnginePreferenceRecordDTO recordDTO) {
        MetadataDTO metadataDTO = new MetadataDTO().source(recordDTO.getMetadata().getSource()).traceId(recordDTO.getMetadata().getTraceId());
        PreferredEngineDTO preferredEngineDTO = new PreferredEngineDTO()
                .email(recordDTO.getData().getEmail())
                .tenant(recordDTO.getData().getTenant())
                .engines(recordDTO.getData().getEngines()
                        .stream()
                        .map(e -> new ProfileCultureEngine().code(e.getCode()).name(e.getName()).preferred(true))
                        .collect(Collectors.toList()));
        PreferredEngineRecordDTO dto = new PreferredEngineRecordDTO().metadata(metadataDTO).data(preferredEngineDTO);
        return dto;
    }
}
