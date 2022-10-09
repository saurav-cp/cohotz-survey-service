//package com.cohotz.survey.service.impl;
//
//import com.cp.unified.dao.account.UserDao;
//import com.cp.unified.dao.survey.CultrBlockDao;
//import com.cp.unified.dao.survey.ScheduledSurveyRequestDao;
//import com.cp.unified.dto.request.survey.SurveyDTO;
//import com.cp.unified.dto.response.block.CultureBlockMin;
//import com.cp.unified.error.CHException;
//import com.cp.unified.model.survey.ScheduledSurveyRequest;
//import com.cp.unified.model.survey.Survey;
//import com.cp.unified.model.survey.SurveyStatus;
//import com.cp.unified.service.SurveyService;
//import com.cp.unified.service.survey.ScheduledSurveyService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class ScheduledSurveyServiceImpl implements ScheduledSurveyService {
//
//    @Autowired
//    SurveyService surveyService;
//
//    @Autowired
//    ScheduledSurveyRequestDao schedSurveyRqDao;
//
//    @Autowired
//    UserDao userDao;
//
//    @Autowired
//    CultrBlockDao blockDao;
//
//    @Value("${cp.update-task.tenants}")
//    List<String> tenants;
//
//    @Override
//    @Async
//    public void createRequest(String tenant, String email, String publisher, String block, boolean self) {
//        if(self) {
//            createRequest(tenant, email, publisher, block);
//        }else{
//            userDao.generateReportingHierarchy(email).ifPresent(rh -> {
//                rh.getReportingHierarchy().forEach(e -> {
//                    createRequest(tenant, e, publisher, block);
//                });
//            });
//        }
//    }
//
//    private void createRequest(String tenant, String email, String publisher, String block){
//        Optional<List<ScheduledSurveyRequest>> existingRequests
//                = schedSurveyRqDao.findByTenantAndParticipantAndBlockAndStatus(tenant, email, block, ScheduledSurveyRequest.Status.PENDING.name());
//
//        if (existingRequests.isPresent() && existingRequests.get().size() > 0) {
//            log.debug("Scheduled Survey Request for participant:{} for block {} all ready exists", email, block);
//        } else {
//            ScheduledSurveyRequest request = new ScheduledSurveyRequest();
//            request.setTenant(tenant);
//            request.setParticipant(email);
//            request.setPublisher(StringUtils.isNotEmpty(publisher)? publisher : email);
//            request.setBlock(block);
//            request.setStatus(ScheduledSurveyRequest.Status.PENDING);
//            schedSurveyRqDao.save(request);
//            log.debug("Created Scheduled Survey Request for participant:{} for block {}", email, block);
//        }
//    }
//
//    //@Scheduled(cron = "0 1 * * * ?") //Should run every day at 2AM UTC
//    @Scheduled(fixedDelay = 300000)
//    public void createSurveyTask(){
//        log.debug("========================START: Create Survey Task========================");
//        tenants.forEach(t -> createSurvey(t));
//        log.debug("========================END  : Create Survey Task========================");
//    }
//
//    @Override
//    @Async
//    public void createSurvey(String tenant) {
//        log.debug("Creating Scheduled Survey for tenant {}", tenant);
//            List<CultureBlockMin> blocks = blockDao.findAllByActiveAsMinRes(true);
//        blocks.forEach(block -> {
//                log.debug("Fetching all pending surveys for block {}", block.getCode());
//                List<ScheduledSurveyRequest> pendingRequests =
//                        schedSurveyRqDao
//                                .findByTenantAndBlockAndStatus(tenant, block.getCode(), ScheduledSurveyRequest.Status.PENDING.name());
//                log.debug("Found {} pending scheduled survey requests for block {}", pendingRequests.size(), block.getCode());
//                if(!pendingRequests.isEmpty()){
//                    SurveyDTO survey = new SurveyDTO();
//                    survey.setStatus(SurveyStatus.PUBLISHED);
//                    survey.setBlock(block.getCode());
//                    survey.setEndDate(LocalDateTime.now().plusDays(14));
//                    survey.setParticipantSource(Survey.ParticipantSource.MANUAL);
//                    survey.setSmartSkip(true);
//                    survey.setType(Survey.SurveyType.AUTOMATIC);
//                    survey.setName(block.getName()+ " "+LocalDate.now().getMonth().name()+ " " + LocalDate.now().getYear());
//                    survey.setDescription("Automated "+ block.getName()+ " Survey - "+LocalDateTime.now());
//                    survey.setTags(
//                            List.of(LocalDateTime.now().toString(),
//                                    LocalDate.now().toString(),
//                                    LocalDate.now().getMonth().name(),
//                                    String.valueOf(LocalDate.now().getYear()),
//                                    "Automated",
//                                    pendingRequests.get(0).getPublisher()
//                                    )
//                            );
//                    survey.setPublisher(pendingRequests.get(0).getPublisher());
//                    survey.setSurveyParticipants(pendingRequests.stream().map(pr -> pr.getParticipant()).collect(Collectors.toSet()));
//                    try {
//                        Survey s = surveyService.createSurveySync(survey, tenant, pendingRequests.get(0).getPublisher());
//                        surveyService.updateSurveyStatus(tenant, s.getId(), SurveyStatus.PUBLISHED.name());
//                        pendingRequests.forEach(pr -> {
//                            pr.setStatus(ScheduledSurveyRequest.Status.COMPLETE);
//                            schedSurveyRqDao.save(pr);
//                        });
//                    } catch (CHException e) {
//                        log.error("Exception while creating automated survey for block {} for tenant: {}", block.getCode(), tenant);
//                    }
//                }
//            });
//
//    }
//}
