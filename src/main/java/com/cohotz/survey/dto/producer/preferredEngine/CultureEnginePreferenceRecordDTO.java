package com.cohotz.survey.dto.producer.preferredEngine;

import com.cohotz.survey.dto.producer.MetadataDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CultureEnginePreferenceRecordDTO {
    private MetadataDTO metadata;
    private CultureEnginePreferenceDTO data;
}
