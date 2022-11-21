package com.cohotz.survey.client.api;

import com.cohotz.survey.client.profile.model.UserRes;
import com.cohotz.survey.client.record.ConsumerApi;
import com.cohotz.survey.client.record.model.EngineScoreRecordDTO;
import com.cohotz.survey.client.record.model.ResponseInsightRecordDTO;
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

import static com.cohotz.survey.SurveyConstants.RECORD_CONSUMER_API_BEAN;
import static com.cohotz.survey.SurveyConstants.RECORD_SERVICE_CLIENT;
import static com.cohotz.survey.error.ServiceCHError.RECORD_SERVICE_DOWN;

@Service
@Slf4j
public class EngineScoreRecordService {

    @Autowired
    @Qualifier(RECORD_CONSUMER_API_BEAN)
    ConsumerApi consumerApi;


    @CircuitBreaker(name = RECORD_SERVICE_CLIENT, fallbackMethod = "engineScoreRecordFallback")
    @Retry(name = RECORD_SERVICE_CLIENT)
    public void createEngineScoreRecord(EngineScoreRecordDTO request) throws CHException {
        try {
            log.debug("[{}] Creating Engine Score Record: [{}] [{}]",
                    RECORD_SERVICE_CLIENT, request.getData().getTenant(), request.getData().getEmail());
            consumerApi.engineScoreRecord(request, request.getData().getTenant());
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                log.error("Error while creating engine score record:  {}", e.getMessage());
                throw new CHException(RECORD_SERVICE_DOWN);
            }else {
                log.error("Exception: {}", e);
                throw e;
            }
        } catch (Exception e) {
            log.error("Exception: {}", e);
            throw e;
        }
    }

    @CircuitBreaker(name = RECORD_SERVICE_CLIENT, fallbackMethod = "responseInsightRecordFallback")
    @Retry(name = RECORD_SERVICE_CLIENT)
    public void createResponseInsightRecord(ResponseInsightRecordDTO request) throws CHException {
        try {
            log.debug("[{}] Creating Response Insight Record: [{}] [{}]",
                    RECORD_SERVICE_CLIENT, request.getData().getTenant(), request.getData().getEmail());
            consumerApi.responseInsightRecord(request, request.getData().getTenant());
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                log.error("Error while creating response insight record:  {}", e.getMessage());
                throw new CHException(RECORD_SERVICE_DOWN);
            }else {
                log.error("Exception: {}", e);
                throw e;
            }
        } catch (Exception e) {
            log.error("Exception: {}", e);
            throw e;
        }
    }



    public void engineScoreRecordFallback(EngineScoreRecordDTO request, Exception e) throws CHException {
        throw new CHException(RECORD_SERVICE_DOWN);
    }

    public void responseInsightRecordFallback(ResponseInsightRecordDTO request, Exception e) throws CHException {
        throw new CHException(RECORD_SERVICE_DOWN);
    }
}
