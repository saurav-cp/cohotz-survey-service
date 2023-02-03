package com.cohotz.survey.dto.request.session;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SessionReq {
    private String profileId;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "FirstName cannot be null")
    @NotBlank(message = "FirstName cannot be blank")
    private String firstName;

    @NotNull(message = "lastName cannot be null")
    @NotBlank(message = "lastName cannot be blank")
    private String lastName;

    @NotNull(message = "Tenant cannot be null")
    @NotBlank(message = "Tenant cannot be blank")
    private String tenant;
}
