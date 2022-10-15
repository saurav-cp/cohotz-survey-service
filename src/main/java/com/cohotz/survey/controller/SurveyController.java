package com.cohotz.survey.controller;

import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.dto.request.SurveyDTO;
import com.cohotz.survey.dto.response.SurveyRes;
import com.cohotz.survey.model.Survey;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.service.SurveyParticipantService;
import com.cohotz.survey.service.SurveyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.cohotz.boot.error.CHException;
import org.cohotz.boot.model.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.cohotz.survey.SurveyConstants.COHOTZ_SURVEY_ENDPOINT;
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
    ApiResponse<List<Survey>> fetchAll(
            @RequestHeader String tenant,
            @RequestParam(value ="status", required = false) String status,
            @RequestParam(value ="search", required = false) String search){
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,service.fetchAll(tenant, status, search));
    }

    @Operation(summary = "Create a new survey")
    @PostMapping
    ApiResponse<Void> create(
            @RequestHeader String tenant,
            @Valid @RequestBody SurveyDTO surveyDto
            ) throws CHException {
        service.createSurvey(surveyDto, tenant, surveyDto.getPublisher());
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
    ApiResponse<SurveyRes> details(
            @RequestHeader String tenant,
            @PathVariable String id) throws CHException {
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,service.details(tenant, id));
    }

    @Operation(summary = "Delete survey")
    @DeleteMapping("/{id}")
    ApiResponse<Void> delete(@RequestHeader String tenant, @PathVariable String id) throws CHException {
        service.deleteSurvey(tenant, id);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }

    @ApiOperation(value = "Update survey")
    @PutMapping("/{id}")
    ApiResponse<Void> update(
            @RequestHeader String tenant,
            @Valid @RequestBody SurveyDTO surveyDto,
            @PathVariable String id) throws CHException {
        service.editSurvey(surveyDto, tenant, id);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }

    @Operation(summary = "Update the status of a survey")
    @PatchMapping("/{id}/status-update")
    ApiResponse<Void> statusUpdate(
            @RequestHeader String tenant,
            @PathVariable String id,
            @RequestParam String status) throws CHException {
        service.updateStatus(tenant, id, status);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }


    @Operation(summary = "Get the survey score")
    @GetMapping("/{tenant}/{id}/score")
    ApiResponse<SurveyRes> surveyScore(@PathVariable String tenant, @PathVariable String id) throws CHException {
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,service.surveyEngineScore(tenant, id));
    }
}
