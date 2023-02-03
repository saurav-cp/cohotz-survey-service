//package com.cohotz.survey.service.azai.impl;
//
//import com.cohotz.survey.client.api.UserService;
//import com.cohotz.survey.dao.azai.SessionDao;
//import com.cohotz.survey.dto.request.azai.ConversationAction;
//import com.cohotz.survey.dto.request.azai.ConversationEngine;
//import com.cohotz.survey.dto.request.session.EventReq;
//import com.cohotz.survey.dto.request.session.SessionReq;
//import com.cohotz.survey.engine.score.record.EngineScore;
//import com.cohotz.survey.model.Participant;
//import com.cohotz.survey.model.azai.session.Event;
//import com.cohotz.survey.model.azai.session.Session;
//import com.cohotz.survey.model.azai.session.SessionContext;
//import com.cohotz.survey.model.azai.session.SessionStatus;
//import com.cohotz.survey.model.azai.survey.AssignedSurvey;
//import com.cohotz.survey.model.azai.survey.QuestionTree;
//import com.cohotz.survey.model.microculture.CohortParticipation;
//import com.cohotz.survey.producer.EngineScoreRecordProducer;
//import com.cohotz.survey.service.SurveyParticipantService;
//import com.cohotz.survey.service.azai.AzAISessionService;
//import com.cp.unified.dto.engine.CultrEngineMin;
//import com.cp.unified.dto.response.block.CultureBlockMin;
//import com.cp.unified.error.CPError;
//import com.cp.unified.error.CPException;
//import com.cp.unified.model.block.CultureBlock;
//import com.cp.unified.model.rubica.profile.RubicaProfile;
//import com.cp.unified.model.rubica.session.*;
//import com.cp.unified.model.user.User;
//import com.cp.unified.model.user.persona.EngineScoreRecord;
//import lombok.extern.slf4j.Slf4j;
//import org.cohotz.boot.auth.AuthenticationInterceptor;
//import org.cohotz.boot.error.CHException;
//import org.cohotz.boot.model.auth.AuthenticatedUser;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import static com.cp.unified.ApplicationConstants.CURRENT_USER;
//
//@Service
//@Slf4j
//public class AzAISessionServiceImpl implements AzAISessionService {
//
//    @Value("${rubica.session.expiry:60}")
//    private long expiryInMins;
//
//    @Autowired
//    SessionDao dao;
//
//    @Autowired
//    AzAISessionService surveyService;
//
//    @Autowired
//    SurveyParticipantService participantService;
//
//    @Autowired
//    EngineScoreRecordProducer scoreRecordProducer;
//
//    @Autowired
//    UserService userService;
//
//    @Override
//    public ConversationAction createSession() {
//        AuthenticatedUser currentUser = AuthenticationInterceptor.currentUser();
//        SessionReq req = new SessionReq(cu.getId(), cu.getEmail(), cu.getFirstName(), cu.getLastName(), cu.getTenant());
//        log.info("Create Session Request - {}", req);
//
//        ConversationAction action = new ConversationAction();
//        List<AssignedSurvey> assignedSurveys = new ArrayList<>();
//        participantService.findAssignedSurveys(true).forEach(as -> {
//            AssignedSurvey assignedSurvey = new AssignedSurvey();
//            BeanUtils.copyProperties(as, assignedSurvey);
//            assignedSurvey.setCohortParticipation(new ArrayList<>());
//            List<CohortParticipation> cohortParticipation = participantService.cohortParticipation(as.getSurveyId(), cu.getTenant());
//            Participant participant = participantService
//                    .findParticipantByAccessCodeForSurvey(as.getSurveyId(), cu.getTenant(), as.getAccessCode());
//            participant.getCohorts().forEach(c -> {
//                log.info("Evaluating: Cohort [{}] with Val: [{}]", c.getName(), c.getValue());
//                cohortParticipation.forEach(cp -> {
//                    log.info("Comparing: Cohort [{}] with CU Cohort: [{}]", cp.getValue(), c.getValue());
//                    if(cp.getName().equals(c.getName()) && cp.getValue().equals(c.getValue())) {
//                        assignedSurvey.getCohortParticipation().add(cp);
//                    }
//                });
//            });
//            assignedSurveys.add(assignedSurvey);
//        });
//        action.setPendingAssignedSurveys(assignedSurveys);
//
//        //Check for an existing active session or create a new session
//        Session session = dao.findByEmailAndActive(cu.getEmail(), true).orElse(null);
//        if(session == null){
//            log.debug("No existing active session found for {}. Creating a new session", cu.getEmail());
//            session = newSession(req, expiryInMins);
//        }
//
//        //Check if the expiry is past current date time
//        if(LocalDateTime.now(ZoneOffset.UTC).isAfter(session.getExpiryAt())){
//            log.info("Session {} has expired. Setting active to false and created new session", session.getId());
//            session.setActive(false);
//            session.setStatus(SessionStatus.ABANDONED);
//            dao.save(session);
//            session = newSession(req, expiryInMins);
//        }
//
//        action.setSessionId(session.getId());
//
//        //TODO: remove the below hardcoded logic with a call to profile service
//        boolean conversationRequired = false;
//        String engine = null;
//        String category = null;
//        try {
//            ConversationEngine conversationEngine = profileUtils
//                    .conversationContext(req.getTenant(), req.getEmail());
//            engine = conversationEngine.getEngine();
//            category = conversationEngine.getCategory();
//            conversationRequired = true;
//        } catch (CHException e) {
//            log.error("Email {} , Tenant {} , Error {}", req.getEmail(), req.getTenant(), e.getError().getDescription());
//        }
//
//
//        //update session with context and save
//        session.setContext(new SessionContext("azAI", engine, category));
//        dao.save(session);
//
//
//
//        if(!conversationRequired){
//            log.info("Conversation not required for {}", req.getEmail());
//            action.setConversation(false);
//            return action;
//        }
//
//
//
//        QuestionTree completeTree = null;
//        try {
//            completeTree = surveyService.fetchOneByEngineAndCategory(engine, category).getQuestionTree();
//        }catch (CHException e){
//            log.error("Need to run survey for engine {} and category {}, but no such survey configured!", engine, category);
//            action.setConversation(false);
//            return action;
//        }
//
//
//
//        //TODO: logic to be improved as per binary tree implementation
//        QuestionTree responseTree = new QuestionTree();
//        if(session.getConversationStatus() == null){
//            //BeanUtils.copyProperties(completeTree, responseTree);
//            log.info("conversation status is null");
//            action.setQuestionTree(new QuestionTree(completeTree.getRoot()));
//        }else if(session.getConversationStatus().getLevel() == 0
//                && session.getConversationStatus().getSentiment().equals("positive")){
//            //BeanUtils.copyProperties(completeTree.getRoot().getPositive(), responseTree);
//            action.setQuestionTree(new QuestionTree(completeTree.getRoot().getPositive()));
//        }else if(session.getConversationStatus().getLevel() == 0
//                && session.getConversationStatus().getSentiment().equals("negative")){
//            //BeanUtils.copyProperties(completeTree.getRoot().getNegative(), responseTree);
//            action.setQuestionTree(new QuestionTree(completeTree.getRoot().getNegative()));
//        }else if(session.getConversationStatus().getLevel() == 1
//                && session.getConversationStatus().getSentiment().equals("positive")){
//            //BeanUtils.copyProperties(completeTree.getRoot().getPositive().getPositive(), responseTree);
//            action.setQuestionTree(new QuestionTree(completeTree.getRoot().getPositive().getPositive()));
//        }
//
//        //action.setQuestionTree(responseTree);
//        return action;
//    }
//
//    @Override
//    public void addEventToSessionConversation(String sessionId, EventReq res) throws Exception {
//        Event event = new Event();
//        BeanUtils.copyProperties(res, event);
//
//        Session session = dao.findById(sessionId).orElseThrow(() -> new Exception());
//
//        if(res.isEnd()){
//            session.setStatus(SessionStatus.COMPLETE);
//            session.setActive(false);
//            //update the profile level persona here
//            RubicaProfile profile = profileService.fetchOneByTenantAndEmail(session.getTenant(), session.getEmail());
//            log.info("Conversation complete for {} wrt engine {} and category {}",
//                    session.getEmail(),
//                    session.getContext().getEngine(),
//                    session.getContext().getCategory());
//            EngineScore engineScore = profile.getPersona().get(session.getContext().getEngine());
//            log.info("Updating Engine {} for profile {} with score {}",
//                    session.getContext().getEngine(),
//                    session.getEmail(),
//                    res.getScore());
//            engineScore.setScore(res.getScore());
//            engineScore.setLastModifiedTS(LocalDateTime.now(ZoneOffset.UTC));
//            profile.getPersona().put(session.getContext().getEngine(), engineScore);
//            profileService.updatePersona(profile.getId(), profile.getPersona());
//
//            EngineScoreRecord record = new EngineScoreRecord();
//            record.setTenant(session.getTenant());
//            record.setEmail(session.getEmail());
//            record.setCreatedTS(LocalDateTime.now(ZoneOffset.UTC));
//            record.setEngine(new CultrEngineMin(session.getContext().getEngine(), session.getContext().getEngine()));
//            record.setBlock(new CultureBlockMin(session.getContext().getType(), session.getContext().getType(), CultureBlock.Type.RUBICA_CONVERSATION, 0, 0));
//            record.setSource(EngineScoreRecord.ScoreSource.RUBICA);
//            record.setAverageScore(res.getScore());
//            record.setTotalScore(res.getScore());
//            record.setResponses(1);
//            engineScoreRecordService.create(record);
//        }else{
//            //renew the session for next 30 mins
//            session.setStatus(SessionStatus.IN_PROGRESS);
//            session.setExpiryDate(LocalDateTime.now(ZoneOffset.UTC).plusMinutes(expiryInMins));
//        }
//
//        //update the conversation status based on response
//        if(session.getConversationStatus() == null) session.setConversationStatus(new ConversationStatus());
//        session.getConversationStatus().setLevel(res.getLevel());
//        session.getConversationStatus().setSentiment(res.getSentiment());
//
//        //add conversation to the session
//        session.getConversation().add(event);
//        session.setLastUpdated(LocalDateTime.now(ZoneOffset.UTC));
//
//        dao.save(session);
//    }
//
//    @Override
//    public void expireSession(String sessionId) throws Exception {
//        log.warn("THIS IS A DEV ONLY FEATURE AND SHOULD NOT BE USED IN HIGHER ENVIRONMENTS!");
//        Session session = dao.findById(sessionId).orElseThrow(() -> new Exception());
//        session.setActive(false);
//        session.setExpiryDate(LocalDateTime.now(ZoneOffset.UTC).minusMinutes(1));
//        dao.save(session);
//        log.info("Successfully force expired session {}", sessionId);
//    }
//
//    @Override
//    public Session findById(String sessionId) throws CHException {
//        return dao.findById(sessionId).orElseThrow(() -> new CPException(CPError.RUBICA_SESSION_NOT_FOUND));
//    }
//
//    @Override
//    public void deleteAll() {
//        dao.deleteAll();
//    }
//
//    private Session newSession(SessionReq req, long expiryInMins){
//        log.debug("Creating new session for {}", req.getEmail());
//        Session session = new Session(LocalDateTime.now(ZoneOffset.UTC).plusMinutes(expiryInMins));
//        BeanUtils.copyProperties(req, session);
//        session.setActive(true);
//        session.setStatus(SessionStatus.CREATED);
//        session.setConversationStatus(null);
//        return dao.save(session);
//    }
//
////    private ConversationEngine conversationContextForProfile(String tenant, String email){
////        ConversationEngine conversationEngine = new ConversationEngine();
////        String engine = null;
////        String category = null;
////        try {
////            ConversationEngine conversationEngine = profileUtils
////                    .conversationContext(tenant, email);
////            engine = conversationEngine.getEngine();
////            category = conversationEngine.getCategory();
////            conversationRequired = true;
////        } catch (CPException e) {
////            log.error("Email {} , Tenant {} , Error {}", email, tenant, e.getError().getDescription());
////        }
////    }
//}
