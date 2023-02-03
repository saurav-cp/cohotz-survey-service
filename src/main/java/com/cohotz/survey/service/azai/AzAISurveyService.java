package com.cohotz.survey.service.azai;

import com.cohotz.survey.dto.request.azai.AutoTreeSurveyReq;
import com.cohotz.survey.model.azai.survey.AutoTreeSurvey;
import org.cohotz.boot.error.CHException;

import java.util.List;

public interface AzAISurveyService {
    List<AutoTreeSurvey> fetchAll();
    AutoTreeSurvey createAutoTreeSurvey(AutoTreeSurveyReq req) throws CHException;
    AutoTreeSurvey updateAutoTreeSurvey(String id, AutoTreeSurveyReq req) throws CHException;
    AutoTreeSurvey fetchOneAutoTreeSurvey(String id) throws CHException;
    AutoTreeSurvey fetchOneByEngineAndCategory(String engineCode, String category) throws CHException;
    void deleteOneAutoTreeSurvey(String id);
    void deleteAll();
}
