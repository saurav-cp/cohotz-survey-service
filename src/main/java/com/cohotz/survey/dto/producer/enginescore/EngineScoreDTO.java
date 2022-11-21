package com.cohotz.survey.dto.producer.enginescore;

import com.cohotz.survey.model.Cohort;
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
public class EngineScoreDTO {
    private String tenant;
    private String email;
    private String reportingTo;
    private List<String> reportingHierarchy;
    private double score;
    private double max;
    private String source;
    private CohotzEntity engine;
    private CohotzEntity block;
    private List<Cohort> cohorts;
 }
