package com.cohotz.survey.dao;

import com.cohotz.survey.model.microculture.UserReporteeParticipationCache;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface UserReporteeParticipationCacheDao extends MongoRepository<UserReporteeParticipationCache, String> {
    Optional<UserReporteeParticipationCache> findByTenantAndCodeAndStartAndEnd(String tenant, String engine, LocalDate start, LocalDate end);
}
