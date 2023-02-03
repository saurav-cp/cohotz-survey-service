package com.cohotz.survey.dao.azai;

import com.cohotz.survey.model.azai.session.Session;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionDao extends MongoRepository<Session, String> {
    Optional<Session> findByEmailAndActive(String email, boolean active);
}
