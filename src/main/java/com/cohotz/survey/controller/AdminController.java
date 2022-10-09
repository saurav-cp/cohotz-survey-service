package com.cohotz.survey.controller;

import com.cohotz.survey.dto.request.SurveyDTO;
import com.cohotz.survey.service.SurveyService;
import com.cohotz.survey.service.SurveyUpdateTask;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.cohotz.boot.error.CHException;
import org.cohotz.boot.model.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.cohotz.boot.CHConstants.RES_GENERIC_SUCCESS_MSG;

@Tag(name = "Admin")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    SurveyService surveyService;

    @Autowired
    SurveyUpdateTask surveyUpdateTask;

    @Operation(summary = "Create a new survey")
    @PostMapping("/surveys/{tenant}")
    ApiResponse<Void> createSurvey(@Valid @RequestBody SurveyDTO surveyDto, @PathVariable String tenant, @RequestParam String publisher) throws CHException {
        surveyService.createSurvey(surveyDto, tenant, publisher);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }

    @Operation(summary = "Update pending surveys, ")
    @PostMapping("/surveys/update/{tenant}")
    ApiResponse<Void> updateSurveys(@PathVariable String tenant){
        surveyUpdateTask.update(tenant);
        return new ApiResponse(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG,null);
    }
}
