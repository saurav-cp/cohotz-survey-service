package com.cohotz.survey.dao;

import com.cohotz.survey.model.Participant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantDao extends MongoRepository<Participant, String> {

    Optional<Participant> findBySurveyIdAndTenantAndEmail(String survey, String tenant, String email);
    Optional<Participant> findBySurveyIdAndTenantAndAccessCode(String survey, String tenant, String accessCode);
    List<Participant> findByTenantAndEmail(String tenant, String email);
    List<Participant> findByTenantAndEmailAndResponseTSGreaterThanEqual(String tenant, String email, LocalDateTime responseTS);
    List<Participant> findByTenantAndSurveyIdAndSurveyCompleteAndScoreAccumulated(String tenant, String surveyId, boolean surveyComplete, boolean scoreAccumulated);
    List<Participant> findByTenantAndSurveyId(String tenant, String surveyId);
    Optional<Participant> findByTenantAndSurveyIdAndId(String tenant, String surveyId, String id);
    void deleteBySurveyId(String surveyId);
    Long countByTenantAndSurveyId(String tenant, String surveyId);
    Long countByTenantAndSurveyIdAndSurveyComplete(String tenant, String surveyId, boolean surveyComplete);

//    @Aggregation(pipeline = {
//            "{'$match': {'tenant': ?0, 'engines.code': {'$in' : ?1}, 'due_dt': {'$gte': ?2 }, 'due_dt': {'$lte': ?3 }}}",
//            "{'$unwind': {'path': '$cohorts'}}",
//            "{'$group': {" +
//                    "'_id': { 'name': '$cohorts.name', 'value': '$cohorts.value', 'month': {'$month': '$created_ts'}, 'year': {'$year': '$created_ts'}}," +
//                    "'response': {$sum: {$cond: [{$eq:['$survey_complete', true]}, 1, 0]}}, " +
//                    "'participants': {$sum: 1} " +
//                    "}" +
//                    "}",
//            "{'$sort': { '_id.month': 1, '_id.year': 1 }}",
//            "{'$group': {" +
//                    "'_id': { 'name': '$_id.name', 'value': '$_id.value'}," +
//                    "'scores': { '$push':  {'current': {'$toInt' :  {'$multiply' : [{'$divide': ['$response', '$participants']}, 100]}}, 'month':'$_id.month', 'year':'$_id.year' } } " +
//                    "}" +
//                    "}",
//            "{'$group': {" +
//                    "'_id': { 'name': '$_id.name'}," +
//                    "'options': { '$push':  {'name':'$_id.value', 'scores': '$scores'} } " +
//                    "}" +
//                    "}",
//            "{'$project': {" +
//                    "'_id': 0," +
//                    "'name': '$_id.name' ," +
//                    "'options': '$options' " +
//                    "}" +
//                    "}",
//            "{'$sort': { 'name': 1 }}",
//    })
//    List<Cohort> participationByCohorts(String tenant, List<String> engine, LocalDate from, LocalDate to);
//
//
//    @Aggregation(pipeline = {
//            "{'$match': {'tenant': ?0, 'engines.code': {'$in' : ?1}, 'due_dt': {'$gte': ?4 }, 'due_dt': {'$lte': ?5 }}}",
//            "{'$unwind': {'path': '$cohorts'}}",
//            "{'$match': {'cohorts.name': ?2, 'cohorts.value': ?3}}",
//            "{'$group': {" +
//                    "'_id': { 'month': {'$month': '$created_ts'}, 'year': {'$year': '$created_ts'}}," +
//                    "'response': {$sum: {$cond: [{$eq:['$survey_complete', true]}, 1, 0]}}, " +
//                    "'participants': {$sum: 1} " +
//                    "}" +
//                    "}",
//            "{'$sort': { '_id.month': 1, '_id.year': 1 }}",
//            "{'$group': {" +
//                    "'_id': 1," +
//                    "'scores': { '$push':  {'current': {'$toInt' :  {'$multiply' : [{'$divide': ['$response', '$participants']}, 100]}}, 'month':'$_id.month', 'year':'$_id.year' } } " +
//                    "}" +
//                    "}",
//            "{'$project': {" +
//                    "'_id': 0," +
//                    "'name': 'Cohort' ," +
//                    "'scores': '$scores' " +
//                    "}" +
//                    "}",
//            "{'$sort': { 'name': 1 }}",
//    })
//    List<Cohort> participationByCohort(String tenant, List<String> engine, String cohort, String cohortOption, LocalDate from, LocalDate to);
}
