package com.cohotz.survey.dao;

import com.cohotz.survey.dto.response.SurveyMinRes;
import com.cohotz.survey.model.Cohort;
import com.cohotz.survey.model.Survey;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyDao extends MongoRepository<Survey, String> {

    void deleteByTenant(String tenant);

    Optional<Survey> findByTenantAndId(String tenant, String id);

    Optional<Survey> findByTenantAndName(String tenant, String name);

//    @Query(value="{'tenant': ?0, 'pub': ?1, 'block.code': ?2, 'status': ?3, 'type': ?4}")
//    Optional<List<Survey>> findByBlockAndStatusAndType(String tenant, String publisher, String block, String status, String type);

    @Query(value="{'tenant': ?0, 'pub': ?1, 'block.code': ?2, 'status': ?3, 'type': ?4}")
    Optional<List<Survey>> findByBlockAndStatusAndType(String tenant, String block, String status, String type);

    @Query(value="{'tenant': ?0}", fields = "{ 'id': 1, 'name':1, 'status':1, 'block.name':1, 'engines': 1, 'end_dt':1 , 'part_count': 1, 'res_count': 1}")
    List<SurveyMinRes> findByTenant(String tenant);

    @Query(value="{'tenant': ?0, 'status': ?1}", fields = "{ 'id': 1, 'name':1, 'status':1, 'block.name':1, 'engines': 1, 'end_dt':1, 'part_count': 1, 'res_count': 1 }")
    List<SurveyMinRes> findByTenantAndStatus(String tenant, String status);

    @Query(value="{'tenant': ?0, 'status': { $in: ?1 }}", fields = "{ 'id': 1, 'name':1, 'status':1, 'block.name':1, 'engines': 1, 'end_dt':1, 'part_count': 1, 'res_count': 1 }")
    List<SurveyMinRes> findByTenantAndStatusIn(String tenant, List<String> statuses);

    List<Survey> findByTenantAndPublisher(String tenant, String publisher);

    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}", fields = "{ 'id': 1, 'name':1, 'status':1 }")
    List<SurveyMinRes> findByNameRegexAndTenantAndStatus(String name, String tenant, String status);

    @Query(value = "{'name': {$regex : ?0, $options: 'i'}, 'tenant' : ?1}", fields = "{ 'id': 1, 'name':1, 'status':1 }")
    List<SurveyMinRes> findByNameRegexAndTenant(String name, String tenant);

    Optional<List<SurveyMinRes>> findByTenantAndStartDateBetween(String tenant, LocalDate startDate, LocalDate endDate);

//    @Aggregation(
//            pipeline = {
//            "{'$match'   : {'tenant': ?0, 'status': {'$in': ['MARKED_COMPLETE', 'AUTO_COMPLETE'] }, 'comp_dt': {'$gte' : ?2 }, 'comp_dt': {'$lte' : ?3 }}}",
//            "{'$unwind'  : {'path': '$engines'}}",
//            "{'$match'   : {'engines.code': {'$in' : ?1}, 'engines.question_count': {'$ne': 0} }}",
//            "{'$group'   : {" +
//                            "'_id': {'year': {'$year': '$comp_dt'}, 'month': {'$month': '$comp_dt'}, 'engine': '$engines.name'}," +
//                            "'events': {'$push' : '$block.name'}," +
//                            "'score': { '$avg': {'$divide': ['$engines.score', '$engines.question_count']}}" +
//                          "}" +
//            "}",
//            "{'$sort': { '_id.month': 1, '_id.year': 1 }}",
//            "{'$group'   : {" +
//                            "'_id': '$_id.engine',"+
//                            "'scores': {'$push': {'year': '$_id.year', 'events': '$events', 'month': '$_id.month', 'day': '1', 'score': '$score'}}"+
//                          "}" +
//            "}",
//            "{'$project' : { '_id': 0, 'engine' : '$_id', 'scores': 1 }}"
//    })
//    List<CultrEngineTrends> surveyTrends(String tenant, List<String> engine, LocalDate from, LocalDate to);

//    @Aggregation(
//            pipeline = {
//                    "{'$match'   : {'tenant': ?0, 'pub' : ?1, 'status': {'$in': ['MARKED_COMPLETE', 'AUTO_COMPLETE'] }, 'comp_dt': {'$gte' : ?3 }, 'comp_dt': {'$lte' : ?4 }}}",
//                    "{'$unwind'  : {'path': '$engines'}}",
//                    "{'$match'   : {'engines.code': {'$in' : ?2}, 'engines.question_count': {'$ne': 0} }}",
//                    "{'$group'   : {" +
//                            "'_id': {'year': {'$year': '$comp_dt'}, 'month': {'$month': '$comp_dt'}, 'engine': '$engines.name'}," +
//                            "'events': {'$push' : '$block.name'}," +
//                            "'score': { '$avg': {'$divide': ['$engines.score', '$engines.question_count']}}" +
//                            "}" +
//                            "}",
//                    "{'$sort': { '_id.month': 1, '_id.year': 1 }}",
//                    "{'$group'   : {" +
//                            "'_id': '$_id.engine',"+
//                            "'scores': {'$push': {'year': '$_id.year', 'events': '$events', 'month': '$_id.month', 'day': '1', 'score': '$score'}}"+
//                            "}" +
//                            "}",
//                    "{'$project' : { '_id': 0, 'engine' : '$_id', 'scores': 1 }}"
//            })
//    List<CultrEngineTrends> surveyTrendsByUser(String tenant, String email, List<String> engine, LocalDate from, LocalDate to);
//
//
//    @Aggregation(
//            pipeline = {
//                    "{'$match'   : {'tenant': ?0, 'block.code': {'$in' : ?1}, 'status': {'$in': ['MARKED_COMPLETE', 'AUTO_COMPLETE'] }, 'comp_dt': {'$gte' : ?2 }, 'comp_dt': {'$lte' : ?3 }}}",
//                    "{'$unwind'  : {'path': '$engines'}}",
//                    "{'$group'   : {" +
//                            "'_id':  '$block'" +
//                            "'score': { '$avg': {'$divide': ['$engines.score', '$engines.question_count']}}" +
//                            "}" +
//                            "}",
//                    "{'$project' : { '_id': 0, 'experience' : {'code':'$_id.code', 'name' : '$_id.name'}, 'score': 1 }}"
//            })
//    List<ExperienceScore> experienceMatrix(String tenant, List<String> blocks, LocalDate from, LocalDate to);

    @Aggregation(pipeline = {
            "{'$match'   : " +
                    "{" +
                    "'tenant': ?0," +
                    " 'status': {'$in': ['MARKED_COMPLETE', 'AUTO_COMPLETE'] }," +
                    " 'comp_dt': {'$gte' : ?2 }, 'comp_dt': {'$lte' : ?3 }" +
                    " 'engines.name': {'$in': ?1}" +
                    "}" +
            "},",
            "{'$group' : " +
                    "{" +
                    "'_id' : '_id'," +
                    "'participants': { '$avg': {'$multiply' : [{'$divide': ['$res_count', '$part_count']}, 100]}}" +
                    " }" +
            "}",
            "{'$project': { '_id': 0, 'participants': 1}}"
    })
    Integer surveyTrendsParticipation(String tenant, List<String> engine, LocalDate from, LocalDate to);

//    @Aggregation(pipeline = {
//            "{'$match': " +
//                    "{" +
//                    "'tenant': ?0, 'engines.code': {'$in': ?1},  'status': {'$in': ['MARKED_COMPLETE', 'AUTO_COMPLETE', 'STARTED'] }, 'comp_dt': {'$gte' : ?2 }, 'comp_dt': {'$lte' : ?3 }" +
//                    "}" +
//            "}",
//            "{'$unwind'  : {'path': '$engines'}}",
//            "{'$match'   : {'engines.code': {'$in': ?1}}}",
//            "{'$group' : " +
//                    "{" +
//                    "'_id' : { 'month': {'$month': '$comp_dt'}, 'year': {'$year': '$comp_dt'}, 'engine': '$engines.name' }," +
//                    "'score': { '$avg': {'$toInt' :  {'$multiply' : [{'$divide': ['$res_count', '$part_count']}, 100]}}}" +
//                    " }" +
//                    "}",
//            "{'$sort': { '_id.month': 1, '_id.year': 1 }}",
//            "{'$group' : " +
//                    "{" +
//                    "'_id' : {'name' : '$_id.engine' } ," +
//                    "'scores': { '$push' : { 'month': '$_id.month', 'year' : '$_id.year', 'current': {'$toInt': '$score'}}}" +
//                    " }" +
//                    "}",
//            "{'$project': { '_id': 0, name: '$_id.name' 'scores': '$scores'}}"
//    })
//    List<Cohort> participationTrends(String tenant, List<String> engine, LocalDate from, LocalDate to);

    @Aggregation(pipeline = {
            "{'$match': " +
                    "{" +
                    "'tenant': ?0, 'engines.code': {'$in': ?1},  'status': {'$in': ['MARKED_COMPLETE', 'AUTO_COMPLETE', 'STARTED'] }, 'comp_dt': {'$gte' : ?2 }, 'comp_dt': {'$lte' : ?3 }" +
                    "}" +
                    "}",
            "{'$group' : " +
                    "{" +
                    "'_id' : { 'month': {'$month': '$comp_dt'}, 'year': {'$year': '$comp_dt'} }," +
                    "'score': { '$avg': {'$toInt' :  {'$multiply' : [{'$divide': ['$res_count', '$part_count']}, 100]}}}" +
                    " }" +
                    "}",
            "{'$sort': { '_id.month': 1, '_id.year': 1 }}",
            "{'$group' : " +
                    "{" +
                    "'_id' : {'name' : 'All' } ," +
                    "'scores': { '$push' : { 'month': '$_id.month', 'year' : '$_id.year', 'current': {'$toInt': '$score'}}}" +
                    " }" +
                    "}",
            "{'$project': { '_id': 0, name: '$_id.name' 'scores': '$scores'}}"
    })
    List<Cohort> participationTrends(String tenant, List<String> engine, LocalDate from, LocalDate to);


    @Aggregation(pipeline = {
            "{'$match'   : " +
                    "{" +
                    "'tenant': ?0," +
                    " 'status': {'$in': ['MARKED_COMPLETE', 'AUTO_COMPLETE', 'STARTED'] }," +
                    " 'comp_dt': {'$gte' : ?2 }, 'comp_dt': {'$lte' : ?3 }" +
                    " 'block.code': ?1" +
                    "}" +
                    "},",
            "{'$group' : " +
                    "{" +
                    "'_id' : '_id'," +
                    "'participants': { '$avg': {'$multiply' : [{'$divide': ['$res_count', '$part_count']}, 100]}}" +
                    " }" +
                    "}",
            "{'$project': { '_id': 0, 'participants': 1}}"
    })
    Integer experienceTrendsParticipation(String tenant, String block, LocalDate from, LocalDate to);

    @Aggregation(pipeline = {
            "{'$match'   : " +
                    "{" +
                    "'tenant': ?0," +
                    " 'pub' : ?1," +
                    " 'status': {'$in': ['MARKED_COMPLETE', 'AUTO_COMPLETE'] }," +
                    " 'comp_dt': {'$gte' : ?3 }, 'comp_dt': {'$lte' : ?4 }" +
                    " 'engines.code': {'$in': ?2}" +
                    "}" +
                    "},",
            "{'$group' : " +
                    "{" +
                    "'_id' : '_id'," +
                    "'participants': { '$avg': {'$multiply' : [{'$divide': ['$res_count', '$part_count']}, 100]}}" +
                    " }" +
                    "}",
            "{'$project': { '_id': 0, 'participants': 1}}"
    })
    Integer surveyTrendsParticipationByUser(String tenant, String email, List<String> engine, LocalDate from, LocalDate to);
}
