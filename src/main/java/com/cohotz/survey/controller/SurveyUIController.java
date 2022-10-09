package com.cohotz.survey.controller;

import com.cohotz.survey.dto.response.ParticipantRes;
import com.cohotz.survey.dto.response.SurveyRes;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.service.SurveyParticipantService;
import com.cohotz.survey.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.cohotz.boot.error.CHException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Slf4j
public class SurveyUIController {

    @Autowired
    SurveyService surveyService;

    @Autowired
    SurveyParticipantService participantService;

    @RequestMapping(value = "/survey/{tenant}/{surveyId}/{accessCode}", method = GET)
    public String surveyForm(@PathVariable String tenant,
                             @PathVariable String surveyId,
                             @PathVariable String accessCode, Model model) throws CHException {
        log.debug("Generating survey form for tenant {}, surveyId {} and accessCode: {}", tenant, surveyId, accessCode);
        ParticipantRes participant = surveyService.participantDetails(tenant, surveyId, accessCode);
        List<StaticSurveyQuestion> questions = surveyService.surveyQuestions(tenant, surveyId, accessCode);
        boolean complete = participant.isSurveyComplete();
        if(complete){
            return "survey-complete";
        }

        model.addAttribute("questions", questions);
        SurveyRes survey = surveyService.details(tenant, surveyId);
        model.addAttribute("surveyName", survey.getName());
        model.addAttribute("surveyDesc", survey.getDescription());
        model.addAttribute("publisher", survey.getPublisher());

        model.addAttribute("tenant", tenant);
        model.addAttribute("surveyId", surveyId);
        model.addAttribute("accessCode", accessCode);
        return "survey";
    }
}
