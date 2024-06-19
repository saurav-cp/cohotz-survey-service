package com.cohotz.survey.controller;

import com.cohotz.survey.client.api.TenantService;
import com.cohotz.survey.client.core.model.TenantSurveyConfigRes;
import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.dto.response.ParticipantRes;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.model.question.StaticSurveyQuestionRes;
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

import java.util.*;

import static com.cohotz.survey.SurveyConstants.COHOTZ_SURVEY_PARTICIPANT_ENDPOINT;
import static org.cohotz.boot.CHConstants.*;

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

    @Autowired
    TenantService tenantService;

    @PreAuthorize(ACCESS_SUPER_ADMIN_ONLY)
    @Operation(summary = "Get all open surveys for email. Accessible on to COHOTZ super admin")
    @GetMapping("/pending-surveys")
    ApiResponse<Map> assignedSurveys(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @PathVariable String surveyId,
            @RequestParam String email
    ) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        Map<String, Object> response = new HashMap<>();
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG, service.findAssignedSurveys(currentTenant, email, true));
    }

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

    @PreAuthorize(ACCESS_ALL_ADMINS)
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
    ApiResponse<StaticSurveyQuestionRes> fetchSurveyQuestions(
            @RequestHeader String tenant,
            @PathVariable String surveyId,
            @PathVariable String accessCode
    ) throws CHException {
        TenantSurveyConfigRes tenantSurveyConfig = tenantService.fetchSurveyConfigByCode(tenant);
        List<StaticSurveyQuestion> questions = surveyService.surveyQuestions(tenant, surveyId, accessCode);
        questions.sort(Comparator.comparing(StaticSurveyQuestion::getPosition));
        StaticSurveyQuestionRes res = StaticSurveyQuestionRes.builder()
                        .questions(questions).tenant(tenantSurveyConfig).build();
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,res);
    }

    @Operation(summary = "Add participant response")
    @PutMapping("/{accessCode}/responses")
    ApiResponse<Void> addResponse(
            @RequestHeader String tenant,
            @RequestHeader(name = "channel", required = false, defaultValue = "WEB") String channel,
            @RequestBody List<ResponseDTO> responses,
            @PathVariable String surveyId,
            @PathVariable String accessCode) throws CHException {
        service.addResponse(tenant, surveyId, accessCode, responses);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }

    @PreAuthorize(ACCESS_ALL_ADMINS)
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
