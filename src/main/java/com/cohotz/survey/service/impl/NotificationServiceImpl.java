package com.cohotz.survey.service.impl;

import com.cohotz.survey.dto.email.ParticipantNotificationDetails;
import com.cohotz.survey.model.Survey;
import com.cohotz.survey.model.notification.twilio.TwilioWhatsappReq;
import com.cohotz.survey.model.notification.twilio.TwilioWhatsappRes;
import com.cohotz.survey.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@ConditionalOnProperty(name = "cohotz.notification.enabled", havingValue = "true")
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${cohotz.notification.enabled:false}")
    private boolean enabled;

    @Value("${cohotz.notification.url}")
    private String url;

    @Override
    public void sendSurvey(Survey survey, ParticipantNotificationDetails details) {
        //Filter by type PHONE is temporary. To be removed
        details.getCommunicationSettings().stream().filter(setting -> setting.getConsent() && "PHONE".equals(setting.getType())).forEach(setting -> {
            String to = setting.getContact();
            if(to != null) {
                String messageTemplate = "Hello SURVEY_PARTICIPANT_NAME,\r\n\r\nYou have been assigned a new survey - *SURVEY_NAME_PLACEHOLDER*\r\n\r\nSurvey Link: SURVEY_LINK\r\n\r\nPlease follow the link to complete the survey.\r\n\r\nNote: The last date to complete the survey is SURVEY_LAST_DATE \r\n\r\nRegards,\r\nSURVEY_TENANT_PLACEHOLDER Team";
                String content = messageTemplate.replace("SURVEY_NAME_PLACEHOLDER", survey.getName()).replace("SURVEY_LINK", details.getLink())
                        .replace("SURVEY_DETAILS_PLACEHOLDER", survey.getDescription())
                        .replace("SURVEY_PARTICIPANT_NAME", details.getName() != null ? details.getName() : "")
                        .replace("SURVEY_TENANT_PLACEHOLDER", survey.getTenant())
                        .replace("SURVEY_LAST_DATE", survey.getEndDate().toString());
                TwilioWhatsappReq req = TwilioWhatsappReq.builder()
                        .to(to)
                        .message(content)
                        .build();
                send(req);
                log.info("Successfully sent survey [{}] to phone [{}]", survey.getName(), to);
            } else {
                log.warn("Unsuccessful in sending survey [{}] to [{}] as phone number is null", survey.getName(), details.getName());
            }
        });

    }

    @Override
    public void sendEngagementSurvey(Survey survey, List<ParticipantNotificationDetails> participants) {

    }

    private TwilioWhatsappRes send(TwilioWhatsappReq request) {
        if(enabled) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "application/json");
            headers.add("content-type", "application/json");

            HttpEntity<TwilioWhatsappReq> requestEntity = new HttpEntity<>(request, headers);
            log.info("Twilio Endpoint: {} and Request: {}", url, request.getMessage());
            ResponseEntity response = restTemplate.exchange(
                    url, HttpMethod.POST, requestEntity,
                    TwilioWhatsappRes.class);
            log.info("Twilio Response: {}", response.getBody());
            return (TwilioWhatsappRes) response.getBody();
        }else{
            log.warn("Notification service is disabled!");
            return null;
        }
    }
}
