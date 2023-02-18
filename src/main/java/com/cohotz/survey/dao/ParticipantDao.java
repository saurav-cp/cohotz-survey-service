package com.cohotz.survey.dao;

import com.cohotz.survey.model.Participant;
import com.cohotz.survey.model.microculture.UserReporteeParticipation;
import com.cohotz.survey.model.score.CHEntityScore;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantDao extends MongoRepository<Participant, String> {
    void deleteByTenant(String tenant);
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

    @Aggregation(pipeline = {
            "{'$match': {'tenant': ?0, 'engines.code': {'$in': ?1}, 'due_dt': {'$gte': ?2, '$lte': ?3 }}}",
            "{'$group': {" +
                    "'_id': { 'month': {'$month': '$due_dt'}, 'year': {'$year': '$due_dt'}}," +
                    "'score': { '$avg': {'$toInt' :  {'$multiply' : [{'$divide': [{ '$sum': {'$cond' : ['$survey_complete', 1, 0]}}, { $sum: 1 }]}, 100]}}}, " +
                    "'count': { '$sum': 1}, " +
                    "}" +
                    "}",
            "{'$sort': { '_id.month': 1, '_id.year': 1 }}",
            "{'$group' : " +
                    "{" +
                    "'_id' : {'name' : 'All' } ," +
                    "'scores': { '$push' : { 'month': '$_id.month', 'year' : '$_id.year', 'value': {'$toInt': '$score'}, 'count': '$count'}}" +
                    " }" +
                    "}",
            "{'$project': { '_id': 0, name: '$_id.name' 'scores': '$scores'}}"
    })
    List<CHEntityScore> engineParticipationTrends(String tenant, List<String> engine, LocalDate from, LocalDate to);

    @Aggregation(pipeline = {
            "{'$match': {'tenant': ?0, 'engines.code': {'$in' : ?1}, 'due_dt': {'$gte': ?2, '$lte': ?3 }}}",
            "{'$unwind': {'path': '$cohorts'}}",
            "{'$group': {" +
                    "'_id': { 'name': '$cohorts.name', 'value': '$cohorts.value', 'month': {'$month': '$created_ts'}, 'year': {'$year': '$created_ts'}}," +
                    "'response': {$sum: {$cond: [{$eq:['$survey_complete', true]}, 1, 0]}}, " +
                    "'participants': {$sum: 1} " +
                    "}" +
                    "}",
            "{'$sort': { '_id.month': 1, '_id.year': 1 }}",
            "{'$group': {" +
                    "'_id': { 'name': '$_id.name', 'value': '$_id.value'}," +
                    "'scores': { '$push':  {'value': {'$toInt' :  {'$multiply' : [{'$divide': ['$response', '$participants']}, 100]}}, 'month':'$_id.month', 'year':'$_id.year', 'count': '$participants' } } " +
                    "}" +
                    "}",
            "{'$group': {" +
                    "'_id': { 'name': '$_id.name'}," +
                    "'options': { '$push':  {'name':'$_id.value', 'scores': '$scores'} } " +
                    "}" +
                    "}",
            "{'$project': {" +
                    "'_id': 0," +
                    "'name': '$_id.name' ," +
                    "'options': '$options' " +
                    "}" +
                    "}",
            "{'$sort': { 'name': 1 }}",
    })
    List<CHEntityScore> engineParticipationCohortStatus(String tenant, List<String> engine, LocalDate from, LocalDate to);

    @Aggregation(pipeline = {
            "{'$match': {'tenant': ?0, 'engines.code': {'$in' : ?1}, 'due_dt': {'$gte': ?4, '$lte': ?5 }}}",
            "{'$unwind': {'path': '$cohorts'}}",
            "{'$match': {'cohorts.name': ?2, 'cohorts.value': ?3}}",
            "{'$group': {" +
                    "'_id': { 'month': {'$month': '$created_ts'}, 'year': {'$year': '$created_ts'}}," +
                    "'response': {$sum: {$cond: [{$eq:['$survey_complete', true]}, 1, 0]}}, " +
                    "'participants': {$sum: 1} " +
                    "}" +
                    "}",
            "{'$sort': { '_id.month': 1, '_id.year': 1 }}",
            "{'$group': {" +
                    "'_id': 1," +
                    "'scores': { '$push':  {'value': {'$toInt' :  {'$multiply' : [{'$divide': ['$response', '$participants']}, 100]}}, 'month':'$_id.month', 'year':'$_id.year', 'count': '$participants' } } " +
                    "}" +
                    "}",
            "{'$project': {" +
                    "'_id': 0," +
                    "'name': 'Cohort' ," +
                    "'scores': '$scores' " +
                    "}" +
                    "}",
            "{'$sort': { 'name': 1 }}",
    })
    List<CHEntityScore> engineParticipationCohortTrends(String tenant, List<String> engine, String cohort, String cohortOption, LocalDate from, LocalDate to);


    @Aggregation(pipeline = {
            "{'$match': {'tenant': ?0, 'block.code': {'$in': ?1}, 'due_dt': {'$gte': ?2, '$lte': ?3 }}}",
            "{'$group': {" +
                    "'_id': { 'month': {'$month': '$due_dt'}, 'year': {'$year': '$due_dt'}}," +
                    "'score': { '$avg': {'$toInt' :  {'$multiply' : [{'$divide': [{ '$sum': {'$cond' : ['$survey_complete', 1, 0]}}, { $sum: 1 }]}, 100]}}}, " +
                    "'count': { '$sum': 1}, " +
                    "}" +
                    "}",
            "{'$sort': { '_id.month': 1, '_id.year': 1 }}",
            "{'$group' : " +
                    "{" +
                    "'_id' : {'name' : 'All' } ," +
                    "'scores': { '$push' : { 'month': '$_id.month', 'year' : '$_id.year', 'value': {'$toInt': '$score'}, 'count': '$count'}}" +
                    " }" +
                    "}",
            "{'$project': { '_id': 0, name: '$_id.name' 'scores': '$scores'}}"
    })
    List<CHEntityScore> experienceParticipationTrends(String tenant, List<String> blocks, LocalDate from, LocalDate to);

    @Aggregation(pipeline = {
            "{'$match': {'tenant': ?0, 'block.code': {'$in' : ?1}, 'due_dt': {'$gte': ?2, '$lte': ?3 }}}",
            "{'$unwind': {'path': '$cohorts'}}",
            "{'$group': {" +
                    "'_id': { 'name': '$cohorts.name', 'value': '$cohorts.value', 'month': {'$month': '$created_ts'}, 'year': {'$year': '$created_ts'}}," +
                    "'response': {$sum: {$cond: [{$eq:['$survey_complete', true]}, 1, 0]}}, " +
                    "'participants': {$sum: 1} " +
                    "}" +
                    "}",
            "{'$sort': { '_id.month': 1, '_id.year': 1 }}",
            "{'$group': {" +
                    "'_id': { 'name': '$_id.name', 'value': '$_id.value'}," +
                    "'scores': { '$push':  {'value': {'$toInt' :  {'$multiply' : [{'$divide': ['$response', '$participants']}, 100]}}, 'month':'$_id.month', 'year':'$_id.year', 'count': '$participants' } } " +
                    "}" +
                    "}",
            "{'$group': {" +
                    "'_id': { 'name': '$_id.name'}," +
                    "'options': { '$push':  {'name':'$_id.value', 'scores': '$scores'} } " +
                    "}" +
                    "}",
            "{'$project': {" +
                    "'_id': 0," +
                    "'name': '$_id.name' ," +
                    "'options': '$options' " +
                    "}" +
                    "}",
            "{'$sort': { 'name': 1 }}",
    })
    List<CHEntityScore> experienceParticipationCohortStatus(String tenant, List<String> blocks, LocalDate from, LocalDate to);

    @Aggregation(pipeline = {
            "{'$match': {'tenant': ?0, 'block.code': {'$in' : ?1}, 'due_dt': {'$gte': ?4, '$lte': ?5 }}}",
            "{'$unwind': {'path': '$cohorts'}}",
            "{'$match': {'cohorts.name': ?2, 'cohorts.value': ?3}}",
            "{'$group': {" +
                    "'_id': { 'month': {'$month': '$created_ts'}, 'year': {'$year': '$created_ts'}}," +
                    "'response': {$sum: {$cond: [{$eq:['$survey_complete', true]}, 1, 0]}}, " +
                    "'participants': {$sum: 1} " +
                    "}" +
                    "}",
            "{'$sort': { '_id.month': 1, '_id.year': 1 }}",
            "{'$group': {" +
                    "'_id': 1," +
                    "'scores': { '$push':  {'value': {'$toInt' :  {'$multiply' : [{'$divide': ['$response', '$participants']}, 100]}}, 'month':'$_id.month', 'year':'$_id.year', 'count': '$participants' } } " +
                    "}" +
                    "}",
            "{'$project': {" +
                    "'_id': 0," +
                    "'name': 'Cohort' ," +
                    "'scores': '$scores' " +
                    "}" +
                    "}",
            "{'$sort': { 'name': 1 }}",
    })
    List<CHEntityScore> experienceParticipationCohortTrends(String tenant, List<String> blocks, String cohort, String cohortOption, LocalDate from, LocalDate to);


    @Aggregation(pipeline = {
            "{'$match': {'tenant': ?0, 'block.code': ?1, 'due_dt': {'$gte': ?2, '$lte': ?3 }, 'reporting_hierarchy': ?4}}",
            "{'$group': {" +
                    "'_id': { 'code': '$block.code', 'name': '$block.name'}," +
                    "'score': { '$avg': {'$toInt' :  {'$multiply' : [{'$divide': [{ '$sum': {'$cond' : ['$survey_complete', 1, 0]}}, { $sum: 1 }]}, 100]}}}, " +
                    "}" +
                    "}",
            "{'$project' : { '_id': 0, 'item' : {'code':'$_id.code', 'name' : '$_id.name'}, 'score': 1 }}"
    })
    Optional<UserReporteeParticipation> reporteeParticipation(String tenant, String block, LocalDate from, LocalDate to, String email);
}
