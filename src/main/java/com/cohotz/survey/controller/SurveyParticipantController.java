package com.cohotz.survey.controller;

import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.dto.response.ParticipantRes;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.service.SurveyParticipantService;
import com.cohotz.survey.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.cohotz.boot.error.CHException;
import org.cohotz.boot.model.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cohotz.survey.SurveyConstants.COHOTZ_SURVEY_PARTICIPANT_ENDPOINT;
import static org.cohotz.boot.CHConstants.RES_GENERIC_SUCCESS_MSG;

@Tag(name = "Participant")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(COHOTZ_SURVEY_PARTICIPANT_ENDPOINT)
@Validated
@Slf4j
public class SurveyParticipantController {

    @Autowired
    SurveyService surveyService;

    @Autowired
    SurveyParticipantService service;

    @Operation(summary = "Get the survey participants. Accessible on to COHOTZ super admin")
    @GetMapping
    ApiResponse<Map> participants(@RequestHeader String tenant, @PathVariable String surveyId) throws CHException {
        Map<String, Object> response = new HashMap<>();
        response.put("participants", service.listParticipant(tenant, surveyId));
        response.put("survey_details", surveyService.details(tenant, surveyId));
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG, response);
    }

    @Operation(summary = "Fetch survey participant details")
    @GetMapping("/{accessCode}")
    ApiResponse<ParticipantRes> participantDetails(
            @RequestHeader String tenant,
            @PathVariable String surveyId,
            @PathVariable String accessCode) throws CHException {
        return new ApiResponse(
                HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG, surveyService.participantDetails(tenant, surveyId, accessCode)
        );
    }

    @Operation(summary = "Get the survey details")
    @GetMapping("/{accessCode}/questions")
    ApiResponse<List<StaticSurveyQuestion>> fetchSurveyQuestions(
            @RequestHeader String tenant,
            @PathVariable String surveyId,
            @PathVariable String accessCode
    ) throws CHException {
        List<StaticSurveyQuestion> questions = surveyService.surveyQuestions(tenant, surveyId, accessCode);
        questions.sort(Comparator.comparing(StaticSurveyQuestion::getPosition));
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,questions);
    }

    @Operation(summary = "Add participant response")
    @PutMapping("/{accessCode}/responses")
    ApiResponse<Void> addResponse(
            @RequestHeader String tenant,
            @RequestBody List<ResponseDTO> responses,
            @PathVariable String surveyId,
            @PathVariable String accessCode) throws CHException {
        service.addResponse(tenant, surveyId, accessCode, responses);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }

    @Operation(summary = "Update participant reminder")
    @PatchMapping("/{accessCode}/last-reminder")
    ApiResponse<Void> updateReminder(
            @RequestHeader String tenant,
            @PathVariable String surveyId,
            @PathVariable String accessCode) throws CHException {
        service.updateReminder(tenant, surveyId, accessCode);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }
}
