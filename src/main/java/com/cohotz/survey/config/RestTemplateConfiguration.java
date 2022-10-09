package com.cohotz.survey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import static com.cohotz.survey.SurveyConstants.CORE_SERVICE_CLIENT;
import static com.cohotz.survey.SurveyConstants.USER_SERVICE_CLIENT;

@Configuration
public class RestTemplateConfiguration {

    @Bean(USER_SERVICE_CLIENT)
    RestTemplate cohotzUserRestTemplate() {
        return new RestTemplate();
    }

    @Bean(CORE_SERVICE_CLIENT)
    @Primary
    RestTemplate cohotzCoreRestTemplate() {
        return new RestTemplate();
    }
}
