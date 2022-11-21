package com.cohotz.survey.dto.producer.preferredEngine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cohotz.boot.model.common.CohotzEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CultureEnginePreferenceDTO {
    private String email;
    private String tenant;
    private List<CohotzEntity> engines;
}
