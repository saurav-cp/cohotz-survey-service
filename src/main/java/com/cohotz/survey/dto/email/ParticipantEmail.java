package com.cohotz.survey.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantEmail {
    private String name;
    private String email;
    private String link;
}
