package com.cohotz.survey.config;

import com.cohotz.survey.client.ApiClient;
import com.cohotz.survey.client.core.CultureBlockApi;
import com.cohotz.survey.client.core.QuestionPoolApi;
import com.cohotz.survey.client.record.ConsumerApi;
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
public class RecordClientConfiguration {

    @Autowired
    @Qualifier(CLIENTS_PREFIX + RECORD_SERVICE_CLIENT)
    RestTemplate restTemplate;

    @Autowired
    CHConfig chConfig;

    @Bean(RECORD_CONSUMER_API_BEAN)
    public ConsumerApi consumerApi() {
        return new ConsumerApi(apiClient());
    }

    @Bean(RECORD_SERVICE_API_CLIENT_BEAN)
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient(restTemplate);
        if(chConfig.getClients().get(RECORD_SERVICE_CLIENT) != null) {
            apiClient.setBasePath(chConfig.getClients().get(RECORD_SERVICE_CLIENT).getUrl());
        }
        apiClient.addDefaultHeader(REQUEST_HEADER_TRACE_ID, MDC.get(LOG_TRACE_ID));
        return apiClient;
    }
}
