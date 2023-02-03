package com.cohotz.survey.dao.azai;

import com.cohotz.survey.model.azai.survey.AutoTreeSurvey;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AutoTreeSurveyDao extends MongoRepository<AutoTreeSurvey, String> {
    Optional<AutoTreeSurvey> findByEngineCodeAndCategory(String engineCode, String category);
}
