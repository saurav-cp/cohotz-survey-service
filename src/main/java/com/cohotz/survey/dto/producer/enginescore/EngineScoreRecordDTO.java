package com.cohotz.survey.dto.producer.enginescore;

import com.cohotz.survey.dto.producer.MetadataDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EngineScoreRecordDTO {
    private MetadataDTO metadata;
    private EngineScoreDTO data;
}
