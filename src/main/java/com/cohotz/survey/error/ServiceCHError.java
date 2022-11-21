package com.cohotz.survey.error;

import org.cohotz.boot.error.CHError;

public enum ServiceCHError implements CHError {

    CORE_SERVICE_DOWN("CP-ERR-SRV-100","Cohotz Core Service Down"),
    PROFILE_SERVICE_DOWN("CP-ERR-SRV-101","Cohotz Profile Service Down"),
    RECORD_SERVICE_DOWN("CP-ERR-SRV-102","Cohotz Record Service Down"),

    ENGINE_NOT_FOUND("CP-ERR-ENG-101","Engine Not Found"),

    CULTR_BLOCK_ALREADY_EXISTS("CP-ERR-BLC-100","Cultr Block already exists"),
    CULTR_BLOCK_NOT_FOUND("CP-ERR-BLC-101","Cultr block not found"),
    CULTR_BLOCK_INVALID_ENGINE("CP-ERR-BLC-102","Invalid Engine used to create block"),
    CULTR_BLOCK_EXISTING_QUESTION_POSITION("CP-ERR-BLC-103","A question already existing in the location. Are you sure you want to replace?"),
    CULTR_BLOCK_QUESTION_AND_BLOCK_ENGINE_MISMATCH("CP-ERR-BLC-104","The Cultr engine associated with the questions you are trying to add is not part of the block. Cannot add question"),

    CULTR_BLOCK_EDIT_ON_ACTIVE("CP-ERR-BLC-104","Editing an active Cultr Block is not permitted. Deactive the block"),
    CULTR_BLOCK_QUESTION_NOT_FOUND("CP-ERR-BLC-105","Question not found"),

    CULTR_QUESTION_NOT_FOUND("CP-ERR-QUES-100","Cultr Question not found"),

    CULTR_SURVEY_NOT_FOUND("CP-ERR-SRVY-100","Cultr survey not found"),
    CULTR_SURVEY_CANNOT_EDIT("CP-ERR-SRVY-101","Cultr survey cannot be edited after publishing"),
    CULTR_SURVEY_STATUS_UPDATE_NOT_ALLOWED("CP-ERR-SRVY-102","Cannot move status of survey"),
    CULTR_SURVEY_ACCESS_CODE_INVALID("CP-ERR-SRVY-103","Invalid access code for the survey"),
    CULTR_SURVEY_CANNOT_DELETE("CP-ERR-SRVY-104","Only draft Cultr survey can be deleted"),
    CULTR_SURVEY_CLOSED("CP-ERR-SRVY-105","Survey is closed for submission"),
    CULTR_SURVEY_INVALID_TENANT("CP-ERR-SRVY-106","Invalid tenant for the survey"),
    CULTR_SURVEY_ALREADY_SUBMITTED("CP-ERR-SRVY-107","Your response for this survey has already been submitted"),
    CULTR_SURVEY_PARTICIPANT_NOT_FOUND("CP-ERR-SRVY-108","Participant not part of the survey"),
    CULTR_SURVEY_RESPONSE_INVALID_OPTION("CP-ERR-SRVY-109","Invalid Option selected in one of the responses"),
    CULTR_SURVEY_MISSING_MANDATORY_RESPONSE("CP-ERR-SRVY-110","One or more mandatory questions have not been answered"),
    CULTR_SURVEY_DUPLICATE_ANSWERS("CP-ERR-SRVY-111","Responses on one or more questions have been repeated"),
    CULTR_SURVEY_NOT_STARTED("CP-ERR-SRVY-112","Responses cannot be accepted for this survey has not started"),
    CH_SURVEY_PUBLISHER_NOT_FOUND("CP-ERR-SRVY-113","Survey Publisher not found"),
    CH_SURVEY_INVALID_PARTICIPANTS_SOURCE("CP-ERR-SRVY-114","Invalid Participants Source"),

    CH_SUBS_ALREADY_EXISTS("CP-ERR-SUBS-100","Subscription already exists"),
    CH_SUBS_NOT_FOUND("CP-ERR-SUBS-101","Subscription not found"),

    TENANT_NOT_FOUND("CP-ERR-TNT-100","Tenant not found"),
    TENANT_MISMATCH("CP-ERR-TNT-101","Tenant Mismatch"),
    TENANT_ALREADY_EXISTS("CP-ERR-TNT-102","Tenant Already Exisits"),
    ;

    private final String code;
    private final String description;

    private ServiceCHError(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "ServiceCHError{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
