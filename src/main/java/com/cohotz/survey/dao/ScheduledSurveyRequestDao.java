package com.cohotz.survey.dao;

import com.cohotz.survey.model.ScheduledSurveyRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduledSurveyRequestDao extends MongoRepository<ScheduledSurveyRequest, String> {
    void deleteByTenant(String tenant);
    Optional<List<ScheduledSurveyRequest>> findByTenantAndParticipantAndBlockAndStatus(String tenant, String participant, String block, String status);
    List<ScheduledSurveyRequest> findByTenantAndBlock(String tenant, String block);
    List<ScheduledSurveyRequest> findByTenantAndBlockAndStatus(String tenant, String block,String status);
}
