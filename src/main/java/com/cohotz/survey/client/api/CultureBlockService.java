package com.cohotz.survey.client.api;

import com.cohotz.survey.client.core.CultureBlockApi;
import com.cohotz.survey.client.core.model.CultureBlock;
import com.cohotz.survey.client.core.model.CultureBlockMin;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.cohotz.boot.error.CHException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.cohotz.survey.SurveyConstants.CORE_SERVICE_CLIENT;
import static com.cohotz.survey.SurveyConstants.CULTURE_BLOCK_API_BEAN;
import static com.cohotz.survey.error.ServiceCHError.CORE_SERVICE_DOWN;
import static com.cohotz.survey.error.ServiceCHError.CULTR_BLOCK_NOT_FOUND;

@Service
@Slf4j
public class CultureBlockService {

    @Autowired
    @Qualifier(CULTURE_BLOCK_API_BEAN)
    CultureBlockApi cultureBlockApi;

    @CircuitBreaker(name = CORE_SERVICE_CLIENT, fallbackMethod = "cbDetailsFallback")
    @Retry(name = CORE_SERVICE_CLIENT)
    public CultureBlock fetchByCode(String tenant, String code) throws CHException {
        try {
            log.debug("[{}] Fetching Culture Block Details: [{}] : [{}]", tenant, code);
            CultureBlock cultureBlock = cultureBlockApi.fetchByCode1(code, tenant).getResult();
            return cultureBlock;
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                log.error("Error while fetching Culture Block Details:  {}", e.getMessage());
                throw new CHException(CULTR_BLOCK_NOT_FOUND);
            }else {
                log.error("Exception: {}", e);
                throw e;
            }
        } catch (Exception e) {
            log.error("Exception: {}", e);
            throw e;
        }
    }

    @CircuitBreaker(name = CORE_SERVICE_CLIENT, fallbackMethod = "cbsDetailsFallback")
    @Retry(name = CORE_SERVICE_CLIENT)
    public List<CultureBlockMin> fetchAll(String tenant) throws CHException {
        try {
            log.debug("[{}] Fetching Culture Blocks Details: [{}] ", tenant);
            List<CultureBlockMin> cultureBlocks = cultureBlockApi
                    .fetchAll4(tenant, null, CultureBlock.TypeEnum.EXPERIENCE.name(),false)
                    .getResult();
            return cultureBlocks;
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                log.error("Error while fetching Culture Block Details:  {}", e.getMessage());
                throw new CHException(CULTR_BLOCK_NOT_FOUND);
            }else {
                log.error("Exception: {}", e);
                throw e;
            }
        } catch (Exception e) {
            log.error("Exception: {}", e);
            throw e;
        }
    }

    public CultureBlock cbDetailsFallback(String tenant, String code, Exception e) throws CHException {
        throw new CHException(CORE_SERVICE_DOWN);
    }

    public List<CultureBlockMin> cbsDetailsFallback(String tenant, Exception e) throws CHException {
        throw new CHException(CORE_SERVICE_DOWN);
    }
}
