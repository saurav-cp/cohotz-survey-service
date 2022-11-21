package com.cohotz.survey.dto.producer.responseInsight;

import com.cohotz.survey.dto.producer.MetadataDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseInsightRecordDTO {
    private MetadataDTO metadata;
    private ResponseInsightDTO data;
}
