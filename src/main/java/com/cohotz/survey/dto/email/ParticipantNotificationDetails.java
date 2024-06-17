package com.cohotz.survey.dto.email;

import com.cohotz.survey.client.profile.model.CommunicationSettings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantNotificationDetails {
    private String name;
    private String email;
    private List<CommunicationSettings> communicationSettings;
    private String link;
}
