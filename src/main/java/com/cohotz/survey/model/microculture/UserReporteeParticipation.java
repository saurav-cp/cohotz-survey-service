package com.cohotz.survey.model.microculture;

import com.cohotz.survey.client.profile.model.UserMinRes;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cohotz.boot.model.common.CohotzEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReporteeParticipation {
    private CohotzEntity item;
    private double score;
    private String email;
    private String name;
    @JsonProperty("reporting_to")
    private String reportingTo;
    private String department;
    @JsonProperty("threshold_met")
    private boolean thresholdMet = false;

    public UserReporteeParticipation(String name, String code, double score) {
        this.item = new CohotzEntity(name, code);
        this.score = score;

    }

    public UserReporteeParticipation(String name, String code, double score, UserMinRes u) {
        this.item = new CohotzEntity(name, code);
        this.score = score;
        this.email = u.getEmail();
        this.name = u.getName();
        this.reportingTo = u.getReportingTo();
        this.department = u.getDepartment();
        this.thresholdMet = false;

    }
}
