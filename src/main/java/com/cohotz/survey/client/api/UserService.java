package com.cohotz.survey.client.api;

import com.cohotz.survey.client.profile.UserApi;
import com.cohotz.survey.client.profile.model.UserRes;
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

import static com.cohotz.survey.SurveyConstants.USER_API_BEAN;
import static com.cohotz.survey.SurveyConstants.USER_SERVICE_CLIENT;
import static com.cohotz.survey.error.ServiceCHError.CH_SURVEY_PUBLISHER_NOT_FOUND;
import static com.cohotz.survey.error.ServiceCHError.PROFILE_SERVICE_DOWN;

@Service
@Slf4j
public class UserService {

    @Autowired
    @Qualifier(USER_API_BEAN)
    UserApi userApi;

    @CircuitBreaker(name = USER_SERVICE_CLIENT, fallbackMethod = "userDetailsFallback")
    @Retry(name = USER_SERVICE_CLIENT)
    public UserRes fetchByTenantAndEmail(String tenant, String email) throws CHException {
        try {
            log.debug("[{}] Fetching User Details: [{}] [{}]", USER_SERVICE_CLIENT, tenant, email);
            UserRes res =  userApi.findByEmailAndTenant(email, tenant).getResult();
            log.debug("[{}] User Details Response:  {}", USER_SERVICE_CLIENT, res);
            return res;
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                log.error("Error while fetching User Details:  {}", e.getMessage());
                throw new CHException(CH_SURVEY_PUBLISHER_NOT_FOUND);
            }else {
                log.error("Exception: {}", e);
                throw e;
            }
        } catch (Exception e) {
            log.error("Exception: {}", e);
            throw e;
        }
    }

    @CircuitBreaker(name = USER_SERVICE_CLIENT, fallbackMethod = "userByReportingFallback")
    @Retry(name = USER_SERVICE_CLIENT)
    public List<UserRes> usersByReporting(String tenant, String email) throws CHException {
        try {
            log.debug("[{}] Fetching User Details: [{}] [{}]", USER_SERVICE_CLIENT, tenant, email);
            return userApi.listUsersReportingTo(email, tenant).getResult();
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                log.error("Error while fetching User Details:  {}", e.getMessage());
                throw new CHException(CH_SURVEY_PUBLISHER_NOT_FOUND);
            }else {
                throw e;
            }
        }
    }

    public UserRes userDetailsFallback(String tenant, String email, Exception e) throws CHException {
        throw new CHException(PROFILE_SERVICE_DOWN);
    }

    @CircuitBreaker(name = USER_SERVICE_CLIENT, fallbackMethod = "reportingHierarchyFallback")
    @Retry(name = USER_SERVICE_CLIENT)
    public List<String> fetchReportingHierarchy(String tenant, String email) throws CHException {
        try {
            log.debug("[{}] Fetching User Details: [{}] [{}]", USER_SERVICE_CLIENT, tenant, email);
            return userApi.findHierarchyByEmailAndTenant(email, tenant).getResult();
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                log.error("Error while fetching User Details:  {}", e.getMessage());
                throw new CHException(CH_SURVEY_PUBLISHER_NOT_FOUND);
            }else {
                throw e;
            }
        }
    }

    public List<UserRes> userByReportingFallback(String tenant, String email, Exception e) throws CHException {
        throw new CHException(PROFILE_SERVICE_DOWN);
    }

    public List<String> reportingHierarchyFallback(String tenant, String email, Exception e) throws CHException {
        throw new CHException(PROFILE_SERVICE_DOWN);
    }



}
