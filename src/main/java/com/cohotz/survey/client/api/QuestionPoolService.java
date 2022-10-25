package com.cohotz.survey.client.api;

import com.cohotz.survey.client.core.QuestionPoolApi;
import com.cohotz.survey.client.core.model.PoolQuestion;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.cohotz.boot.error.CHException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import static com.cohotz.survey.SurveyConstants.CORE_SERVICE_CLIENT;
import static com.cohotz.survey.SurveyConstants.QUESTION_POOL_API_BEAN;
import static com.cohotz.survey.error.ServiceCHError.CORE_SERVICE_DOWN;
import static com.cohotz.survey.error.ServiceCHError.CULTR_QUESTION_NOT_FOUND;

@Service
@Slf4j
public class QuestionPoolService {

    @Autowired
    @Qualifier(QUESTION_POOL_API_BEAN)
    QuestionPoolApi questionPoolApi;

    @Autowired
    ApplicationContext context;

    @CircuitBreaker(name = CORE_SERVICE_CLIENT, fallbackMethod = "quesPoolFallback")
    @Retry(name = CORE_SERVICE_CLIENT)
    public PoolQuestion fetchByTenantAndCode(String tenant, String code) throws CHException {
        try {
            log.debug("[{}] Fetching Pool Question Details: [{}] : [{}]", CORE_SERVICE_CLIENT, tenant, code);
            PoolQuestion result = questionPoolApi.questionDetails(code, tenant).getResult();
            //QuestionManager qm = (QuestionManager) context.getBean(result.getResponseType());
            return result;
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                log.error("Error while fetching Pool Question Details:  [{}]", e.getMessage());
                throw new CHException(CULTR_QUESTION_NOT_FOUND);
            }else {
                log.error("Exception: {}", e);
                throw e;
            }
        } catch (Exception e) {
            log.error("Exception: {}", e);
            throw e;
        }
    }

    public PoolQuestion quesPoolFallback(String tenant, String code, Exception e) throws CHException {
        throw new CHException(CORE_SERVICE_DOWN);
    }
}
