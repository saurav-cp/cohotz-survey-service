package com.cohotz.survey.service.azai;

import com.cohotz.survey.dto.request.azai.ConversationAction;
import com.cohotz.survey.dto.request.session.EventReq;
import com.cohotz.survey.model.azai.session.Session;
import org.cohotz.boot.error.CHException;

public interface AzAISessionService {
    ConversationAction createSession();
    void addEventToSessionConversation(String sessionId, EventReq res) throws Exception;
    void expireSession(String sessionId) throws Exception;
    Session findById(String sessionId) throws CHException;
    void deleteAll();
}
