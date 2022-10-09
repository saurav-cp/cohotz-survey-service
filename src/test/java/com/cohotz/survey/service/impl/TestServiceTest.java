//package com.cohotz.survey.service.impl;
//
//import com.cohotz.survey.CohotzSurveyServiceApplication;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
////@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles({ "it" })
//@Slf4j
//class TestServiceTest {
//
//    private MockMvc mvc;
//
//    // bind the above RANDOM_PORT
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private WebApplicationContext wac;
//
//    @Autowired
//    TestService service;
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void printEngines() {
//        service.printEngines();
//    }
//}