package com.cohotz.survey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import static com.cohotz.survey.SurveyConstants.*;

@Configuration
public class RestTemplateConfiguration {

    @Bean(CLIENTS_PREFIX + USER_SERVICE_CLIENT)
    RestTemplate cohotzUserRestTemplate() {
        return new RestTemplate();
    }

    @Bean(CLIENTS_PREFIX + CORE_SERVICE_CLIENT)
    @Primary
    RestTemplate cohotzCoreRestTemplate() {
        return new RestTemplate();
    }

    @Bean(CLIENTS_PREFIX + RECORD_SERVICE_CLIENT)
    RestTemplate cohotzRecordRestTemplate() {
        return new RestTemplate();
    }
}
