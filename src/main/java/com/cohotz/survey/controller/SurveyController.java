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

//    @Autowired
//    ScheduledSurveyService scheduledService;

    @Operation(summary = "Get all the surveys for a tenant. Optionally it can also be filtered with status")
    @GetMapping("/{tenant}")
    ApiResponse<List<Survey>> fetchAllForTenant(@PathVariable String tenant,
                                                @RequestParam(value ="status", required = false) String status,
                                                @RequestParam(value ="search", required = false) String search){
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,service.fetchAll(tenant, status, search));
    }

    @Operation(summary = "Create a new survey")
    @PostMapping("/{tenant}")
    ApiResponse<Void> createSurvey(@Valid @RequestBody SurveyDTO surveyDto,@PathVariable String tenant) throws CHException {
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

    @Operation(summary = "Get the survey details")
    @GetMapping("/{tenant}/{id}")
    ApiResponse<SurveyRes> surveyDetails(@PathVariable String tenant, @PathVariable String id) throws CHException {
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,service.details(tenant, id));
    }

    @Operation(summary = "Get the survey score")
    @GetMapping("/{tenant}/{id}/score")
    ApiResponse<SurveyRes> surveyScore(@PathVariable String tenant, @PathVariable String id) throws CHException {
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,service.surveyEngineScore(tenant, id));
    }

//    @Operation(summary = "Get the survey insight")
//    @GetMapping("/{tenant}/{id}/insight")
//    ApiResponse<List<SurveyQuestionScore>> surveyInsight(@PathVariable String tenant, @PathVariable String id)
//            throws CHException {
//        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,service.surveyQuestionScore(tenant, id));
//    }

    @Operation(summary = "Get the survey participants. Accessible on to CULTR PLUS super admin")
    @GetMapping("/{tenant}/{id}/participants")
    ApiResponse<Map> participants(@PathVariable String tenant, @PathVariable String id) throws CHException {
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,service.listParticipant(tenant, id));
    }

    @Operation(summary = "Get the survey participant details including response")
    @GetMapping("/{tenant}/{id}/participants/{accessCode}")
    ApiResponse<Survey> participantDetails(
            @PathVariable String tenant,
            @PathVariable String id,
            @PathVariable String accessCode) throws CHException {
        return new ApiResponse(
                HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,service.participantDetails(tenant, id, accessCode)
        );
    }

    @Operation(summary = "Get the survey details")
    @GetMapping("/{tenant}/{id}/questions/{accessCode}")
    ApiResponse<List<StaticSurveyQuestion>> fetchSurveyQuestions(
            @PathVariable String tenant,
            @PathVariable String id,
            @PathVariable String accessCode
    ) throws CHException {
        List<StaticSurveyQuestion> questions = service.surveyQuestions(tenant, id, accessCode);
        questions.sort(Comparator.comparing(StaticSurveyQuestion::getPosition));
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,questions);
    }


    @Operation(summary = "Delete a DRAFT survey")
    @DeleteMapping("/{tenant}/{id}")
    ApiResponse<Void> deleteSurvey(@PathVariable String tenant, @PathVariable String id) throws CHException {
        service.deleteSurvey(tenant, id);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }

    @ApiOperation(value = "Update a DRAFT survey")
    @PutMapping("/{tenant}/{id}")
    ApiResponse<Void> updateSurvey(@Valid @RequestBody SurveyDTO surveyDto,
                                   @PathVariable String tenant,
                                   @PathVariable String id) throws CHException {
        service.editSurvey(surveyDto, tenant, id);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }

    @Operation(summary = "Update the status of a survey")
    @PutMapping("/{tenant}/{id}/statusUpdate/{status}")
    ApiResponse<Void> updateSurveyStatus(@PathVariable String tenant,
                                         @PathVariable String id, @PathVariable String status) throws CHException {
        service.updateSurveyStatus(tenant, id, status);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }

    @Operation(summary = "Add participant response")
    @PutMapping("/{tenant}/{id}/responses/{accessCode}")
            ApiResponse<Void> addResponse(@RequestBody List<ResponseDTO> responses,
                                  @PathVariable String tenant,
                                  @PathVariable String id,
                                  @PathVariable String accessCode) throws CHException {
        service.addResponse(responses, tenant, id, accessCode);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }

    @Operation(summary = "Update participant reminder")
    @PutMapping("/{tenant}/{id}/responses/{accessCode}/update-last-reminder")
    ApiResponse<Void> updateReminderForUser(
            @PathVariable String tenant,
            @PathVariable String id,
            @PathVariable String accessCode) throws CHException {
        participantService.updateReminder(tenant, id, accessCode);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }
}
