package com.cohotz.survey.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantMinRes {
    private boolean complete;
    private String link;
    private String email;
}
