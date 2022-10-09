package com.cohotz.survey.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SurveyStatus {
    DRAFT("DRAFT", "Draft"),
    ERROR("ERROR", "Error"),
    PUBLISHED("PUBLISHED", "Published"),
    STARTED("STARTED", "Started"),
    CANCELLED("CANCELLED", "Cancelled"),
    AUTO_COMPLETE("AUTO_COMPLETE", "Auto Complete"),
    MARKED_COMPLETE("MARKED_COMPLETE", "Marked Complete"),
    NO_RESPONSE_COMPLETE("NO_RESPONSE_COMPLETE", "No Response"),
    NO_VALID_PARTICIPANTS_ERROR("NO_VALID_PARTICIPANTS_ERROR", "No Valid Participants");
    private final String code;
    private final String displayName;

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    SurveyStatus(String code, String displayName){
        this.code = code;
        this.displayName = displayName;
    }
    public boolean surveyClosed(){
        if(this.equals(CANCELLED) || this.equals(AUTO_COMPLETE) ||
                this.equals(MARKED_COMPLETE) || this.equals(NO_VALID_PARTICIPANTS_ERROR) || this.equals(ERROR)){
            return true;
        }
        return false;
    }

    public boolean surveyNotStarted(){
        if(this.equals(DRAFT) || this.equals(PUBLISHED) || this.equals(NO_VALID_PARTICIPANTS_ERROR) || this.equals(ERROR)){
            return true;
        }
        return false;
    }

    public boolean inProgress(){
        if(this.equals(STARTED)){
            return true;
        }
        return false;
    }
}
