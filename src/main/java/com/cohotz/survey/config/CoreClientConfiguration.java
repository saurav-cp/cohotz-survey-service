package com.cohotz.survey.config;

import com.cohotz.survey.client.ApiClient;
import com.cohotz.survey.client.core.CultureBlockApi;
import com.cohotz.survey.client.core.QuestionPoolApi;
import org.cohotz.boot.config.CHConfig;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import static com.cohotz.survey.SurveyConstants.*;
import static org.cohotz.boot.CHConstants.LOG_TRACE_ID;
import static org.cohotz.boot.CHConstants.REQUEST_HEADER_TRACE_ID;

@Configuration
public class CoreClientConfiguration {

    @Autowired
    @Qualifier(CORE_SERVICE_CLIENT)
    RestTemplate restTemplate;

    @Autowired
    CHConfig chConfig;

    @Bean(CULTURE_BLOCK_API_BEAN)
    public CultureBlockApi cultureBlockApi() {
        return new CultureBlockApi(apiClient());
    }

    @Bean(QUESTION_POOL_API_BEAN)
    public QuestionPoolApi questionPoolApi() {
        return new QuestionPoolApi(apiClient());
    }

    @Bean(CORE_SERVICE_API_CLIENT_BEAN)
    @Primary
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient(restTemplate);
        if(chConfig.getClients().get(CORE_SERVICE_CLIENT) != null) {
            apiClient.setBasePath(chConfig.getClients().get(CORE_SERVICE_CLIENT).getUrl());
        }
        apiClient.addDefaultHeader(REQUEST_HEADER_TRACE_ID, MDC.get(LOG_TRACE_ID));
        return apiClient;
    }
}
