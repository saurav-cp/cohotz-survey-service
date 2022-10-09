package com.cohotz.survey.config;

import com.cohotz.survey.model.config.SurveyScheduler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "survey")
@Getter
@Setter
public class SurveyConfiguration {
    private Map<String, List<String>> statusMap;
    private Map<String, String> cohorts;
    private String surveyLinkFormat;
    private long smartThreshold;
    private long updateDelay;
    private SurveyScheduler scheduler;
}
