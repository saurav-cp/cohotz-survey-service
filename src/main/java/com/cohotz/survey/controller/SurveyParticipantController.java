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
import org.cohotz.boot.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cohotz.survey.SurveyConstants.COHOTZ_SURVEY_PARTICIPANT_ENDPOINT;
import static org.cohotz.boot.CHConstants.*;

@Tag(name = "Participant")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(COHOTZ_SURVEY_PARTICIPANT_ENDPOINT)
@Validated
@Slf4j
@PreAuthorize(ACCESS_ALL_ADMINS)
public class SurveyParticipantController {

    @Autowired
    SurveyService surveyService;

    @Autowired
    SurveyParticipantService service;

    @PreAuthorize(ACCESS_SUPER_ADMIN_ONLY)
    @Operation(summary = "Get the survey participants. Accessible on to COHOTZ super admin")
    @GetMapping
    ApiResponse<Map> participants(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @PathVariable String surveyId) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        Map<String, Object> response = new HashMap<>();
        response.put("participants", service.listParticipant(currentTenant, surveyId));
        response.put("survey_details", surveyService.details(currentTenant, surveyId));
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG, response);
    }

    @Operation(summary = "Fetch survey participant details")
    @GetMapping("/{accessCode}")
    ApiResponse<ParticipantRes> participantDetails(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @PathVariable String surveyId,
            @PathVariable String accessCode) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        return new ApiResponse(
                HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG, surveyService.participantDetails(currentTenant, surveyId, accessCode)
        );
    }

    @Operation(summary = "Get the survey details")
    @GetMapping("/{accessCode}/questions")
    ApiResponse<List<StaticSurveyQuestion>> fetchSurveyQuestions(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @PathVariable String surveyId,
            @PathVariable String accessCode
    ) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        List<StaticSurveyQuestion> questions = surveyService.surveyQuestions(currentTenant, surveyId, accessCode);
        questions.sort(Comparator.comparing(StaticSurveyQuestion::getPosition));
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,questions);
    }

    @Operation(summary = "Add participant response")
    @PutMapping("/{accessCode}/responses")
    ApiResponse<Void> addResponse(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @RequestBody List<ResponseDTO> responses,
            @PathVariable String surveyId,
            @PathVariable String accessCode) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        service.addResponse(currentTenant, surveyId, accessCode, responses);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }

    @Operation(summary = "Update participant reminder")
    @PatchMapping("/{accessCode}/last-reminder")
    ApiResponse<Void> updateReminder(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @PathVariable String surveyId,
            @PathVariable String accessCode) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        service.updateReminder(currentTenant, surveyId, accessCode);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }
}
