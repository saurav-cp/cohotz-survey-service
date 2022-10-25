package com.cohotz.survey.controller;

import com.cohotz.survey.dto.request.SurveyDTO;
import com.cohotz.survey.dto.response.SurveyRes;
import com.cohotz.survey.model.Survey;
import com.cohotz.survey.service.SurveyParticipantService;
import com.cohotz.survey.service.SurveyService;
import io.swagger.annotations.ApiOperation;
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

import javax.validation.Valid;
import java.util.List;

import static com.cohotz.survey.SurveyConstants.COHOTZ_SURVEY_ENDPOINT;
import static org.cohotz.boot.CHConstants.ACCESS_ALL_ADMINS;
import static org.cohotz.boot.CHConstants.RES_GENERIC_SUCCESS_MSG;

@Tag(name = "Survey")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(COHOTZ_SURVEY_ENDPOINT)
@Validated
@Slf4j
public class SurveyController {

    @Autowired
    SurveyService service;

    @Autowired
    SurveyParticipantService participantService;


    @Operation(summary = "Fetch All Surveys. Optionally it can also be filter based on status and search query")
    @GetMapping
    @PreAuthorize(ACCESS_ALL_ADMINS)
    ApiResponse<List<Survey>> fetchAll(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @RequestParam(value ="status", required = false) String status,
            @RequestParam(value ="search", required = false) String search) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,service.fetchAll(currentTenant, status, search));
    }

    @Operation(summary = "Create a new survey")
    @PostMapping
    @PreAuthorize(ACCESS_ALL_ADMINS)
    ApiResponse<Void> create(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @Valid @RequestBody SurveyDTO surveyDto
            ) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        service.createSurvey(surveyDto, currentTenant, surveyDto.getPublisher());
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }

//    @Operation(summary = "Create a scheduled experience survey")
//    @PostMapping("/{tenant}/scheduled")
//    ApiResponse<Void> createScheduledSurvey(
//            @PathVariable String tenant,
//            @RequestParam String block,
//            @RequestParam boolean self,
//            @RequestParam String email) {
//        scheduledService.createRequest(tenant, email, currentUser.getEmail(), block,self );
//        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
//    }

    @Operation(summary = "Fetch survey details")
    @GetMapping("/{id}")
    @PreAuthorize(ACCESS_ALL_ADMINS)
    ApiResponse<SurveyRes> details(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @PathVariable String id) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,service.details(currentTenant, id));
    }

    @Operation(summary = "Delete survey")
    @DeleteMapping("/{id}")
    @PreAuthorize(ACCESS_ALL_ADMINS)
    ApiResponse<Void> delete(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @PathVariable String id) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        service.deleteSurvey(currentTenant, id);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }

    @ApiOperation(value = "Update survey")
    @PutMapping("/{id}")
    @PreAuthorize(ACCESS_ALL_ADMINS)
    ApiResponse<Void> update(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @Valid @RequestBody SurveyDTO surveyDto,
            @PathVariable String id) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        service.editSurvey(surveyDto, currentTenant, id);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }

    @Operation(summary = "Update the status of a survey")
    @PatchMapping("/{id}/status-update")
    @PreAuthorize(ACCESS_ALL_ADMINS)
    ApiResponse<Void> statusUpdate(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @PathVariable String id,
            @RequestParam String status) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        service.updateStatus(currentTenant, id, status);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }


    @Operation(summary = "Get the survey score")
    @GetMapping("/{tenant}/{id}/score")
    @PreAuthorize(ACCESS_ALL_ADMINS)
    ApiResponse<SurveyRes> surveyScore(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @PathVariable String id) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,service.surveyEngineScore(currentTenant, id));
    }
}
