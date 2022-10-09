package com.cohotz.survey.config;

import com.cohotz.survey.manager.QuestionManager;
import com.cohotz.survey.manager.impl.ChoiceQuestionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResponseConfiguration {

    @Bean("SINGLE_SELECT")
    QuestionManager singleSelectRespManager(){
        return new ChoiceQuestionManager();
    }

    @Bean("MULTI_SELECT")
    QuestionManager multiSelectRespManager(){
        return new ChoiceQuestionManager();
    }

//    @Bean("MULTI_SELECT_INFO")
//    QuestionManager multiSelectInfoRespManager(){
//        return new ChoiceInfoQuestionManager(userDao);
//    }
}
