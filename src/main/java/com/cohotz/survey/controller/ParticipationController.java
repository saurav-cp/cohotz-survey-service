package com.cohotz.survey.controller;

import com.cohotz.survey.dto.request.FilterBy;
import com.cohotz.survey.dto.request.ResponseDTO;
import com.cohotz.survey.dto.response.ParticipantRes;
import com.cohotz.survey.model.question.StaticSurveyQuestion;
import com.cohotz.survey.model.score.CHEntityScore;
import com.cohotz.survey.service.ParticipationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cohotz.boot.error.CHException;
import org.cohotz.boot.model.response.ApiResponse;
import org.cohotz.boot.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

import static com.cohotz.survey.SurveyConstants.COHOTZ_PARTICIPATION_ENDPOINT;
import static org.cohotz.boot.CHConstants.ACCESS_ALL_ADMINS;
import static org.cohotz.boot.CHConstants.RES_GENERIC_SUCCESS_MSG;

@Tag(name = "Participation")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(COHOTZ_PARTICIPATION_ENDPOINT)
@Validated
@Slf4j
@PreAuthorize(ACCESS_ALL_ADMINS)
public class ParticipationController {

    @Autowired
    ParticipationService service;

    @Operation(summary = "Participation Trends")
    @GetMapping("/trends")
    public ApiResponse<List<CHEntityScore>> participationTrends(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @RequestParam(required = false, defaultValue = "ENGINES") FilterBy filterBy,
            @RequestParam List<String> filters,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().minusMonths(1).withDayOfMonth(1)}") LocalDate from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().plusDays(1)}") LocalDate to) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        List<CHEntityScore> response = new ArrayList<>();
        if(filterBy.equals(FilterBy.ENGINES)) {
            response = service.engineParticipationTrends(currentTenant, filters, from, to);
        }else if(filterBy.equals(FilterBy.BLOCKS)) {
            response = service.experienceParticipationTrends(currentTenant, filters, from, to);
        }
        return new ApiResponse<>(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG, response);
    }

    @Operation(summary = "Participation Cohort Trends")
    @GetMapping("/trends/cohorts")
    public ApiResponse<List<CHEntityScore>> participationCohortTrends(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @RequestParam String cohort,
            @RequestParam String cohortOption,
            @RequestParam(required = false, defaultValue = "ENGINES") FilterBy filterBy,
            @RequestParam List<String> filters,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().minusMonths(1).withDayOfMonth(1)}") LocalDate from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().plusDays(1)}") LocalDate to) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        List<CHEntityScore> response = new ArrayList<>();
        if(filterBy.equals(FilterBy.ENGINES)) {
            response = service.engineParticipationCohortTrends(currentTenant, filters, cohort, cohortOption, from, to);
        }else if(filterBy.equals(FilterBy.BLOCKS)) {
            response = service.experienceParticipationCohortTrends(currentTenant, filters, cohort, cohortOption, from, to);
        }
        return new ApiResponse<>(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG, response);
    }


    @GetMapping("/status/cohorts")
    public ApiResponse<List<CHEntityScore>> participationByCohorts(
            @RequestHeader(name = "tenant", required = false) String tenant,
            @RequestParam(required = false, defaultValue = "ENGINES") FilterBy filterBy,
            @RequestParam List<String> filters,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().minusMonths(1).withDayOfMonth(1)}") LocalDate from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().plusDays(1)}") LocalDate to) throws CHException {
        String currentTenant = RequestUtils.tenant(tenant);
        List<CHEntityScore> response = new ArrayList<>();
        if(filterBy.equals(FilterBy.ENGINES)) {
            response = service.engineParticipationCohortStatus(currentTenant, filters, from, to);
        } else if(filterBy.equals(FilterBy.BLOCKS)) {
            response = service.experienceParticipationCohortStatus(currentTenant, filters, from, to);
        }
        return new ApiResponse<>(HttpStatus.OK.value(), RES_GENERIC_SUCCESS_MSG, response);
    }

}
