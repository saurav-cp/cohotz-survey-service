package com.cohotz.survey.client.api;

import com.cohotz.survey.client.core.TenantApi;
import com.cohotz.survey.client.core.model.CultureBlock;
import com.cohotz.survey.client.core.model.CultureBlockMin;
import com.cohotz.survey.client.core.model.Tenant;
import com.cohotz.survey.client.core.model.TenantRes;
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
import static com.cohotz.survey.SurveyConstants.TENANT_API_BEAN;
import static com.cohotz.survey.error.ServiceCHError.*;

@Service
@Slf4j
public class TenantService {

    @Autowired
    @Qualifier(TENANT_API_BEAN)
    TenantApi tenantApi;

    @CircuitBreaker(name = CORE_SERVICE_CLIENT, fallbackMethod = "tenantDetailsFallback")
    @Retry(name = CORE_SERVICE_CLIENT)
    public TenantRes fetchByCode(String code) throws CHException {
        try {
            log.debug("[{}] Fetching tenant Details: [{}]", code);
            TenantRes tenant = tenantApi.fetchByCode(code).getResult();
            log.info("Tenant Detail: [{}]", tenant);
            return tenant;
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                log.error("Error while fetching Tenant Details:  {}", e.getMessage());
                throw new CHException(TENANT_NOT_FOUND);
            }else {
                log.error("Exception: {}", e);
                throw e;
            }
        } catch (Exception e) {
            log.error("Exception: {}", e);
            throw e;
        }
    }


    public TenantRes tenantDetailsFallback(String code, Exception e) throws CHException {
        throw new CHException(CORE_SERVICE_DOWN);
    }
}
