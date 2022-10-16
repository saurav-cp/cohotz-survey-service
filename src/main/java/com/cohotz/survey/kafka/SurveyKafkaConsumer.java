//package com.cohotz.survey.kafka;
//
//import com.cohotz.survey.create.CreateSurveyMessage;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//import static com.cohotz.survey.SurveyConstants.CREATE_SURVEY_LISTENER_GROUP_ID;
//import static com.cohotz.survey.SurveyConstants.CREATE_SURVEY_TOPIC;
//
//@Slf4j
//@Component
//public class SurveyKafkaConsumer {
//
//    @KafkaListener(topics = CREATE_SURVEY_TOPIC, groupId = CREATE_SURVEY_LISTENER_GROUP_ID)
//    public void createSurvey(CreateSurveyMessage message) {
//        log.info("CreateSurveyMessage: {}", message);
//    }
//}
