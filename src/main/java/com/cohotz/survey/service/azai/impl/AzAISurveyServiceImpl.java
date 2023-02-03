package com.cohotz.survey.service.azai.impl;

import com.cohotz.survey.dao.azai.AutoTreeSurveyDao;
import com.cohotz.survey.dto.request.azai.AutoTreeSurveyReq;
import com.cohotz.survey.model.azai.survey.AutoTreeSurvey;
import com.cohotz.survey.service.azai.AzAISurveyService;
import lombok.extern.slf4j.Slf4j;
import org.cohotz.boot.error.CHException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cohotz.survey.error.ServiceCHError.CH_SURVEY_ALREADY_EXISTS;
import static com.cohotz.survey.error.ServiceCHError.CH_SURVEY_NOT_FOUND;

@Service
@Slf4j
public class AzAISurveyServiceImpl implements AzAISurveyService {

    @Autowired
    AutoTreeSurveyDao dao;

    @Override
    public List<AutoTreeSurvey> fetchAll() {
        return dao.findAll();
    }

    @Override
    public AutoTreeSurvey createAutoTreeSurvey(AutoTreeSurveyReq req) throws CHException {
        if(dao.findByEngineCodeAndCategory(req.getEngineCode(), req.getCategory()).isPresent()) {
            log.error("Survey exists in database with engine {} and category {}", req.getEngineCode(), req.getCategory());
            throw new CHException(CH_SURVEY_ALREADY_EXISTS);
        }

        AutoTreeSurvey survey = new AutoTreeSurvey();
        BeanUtils.copyProperties(req, survey);
        return dao.save(survey);
    }

    @Override
    public AutoTreeSurvey updateAutoTreeSurvey(String id, AutoTreeSurveyReq req) throws CHException {
        AutoTreeSurvey survey = dao.findById(id).orElseThrow(() -> new CHException(CH_SURVEY_NOT_FOUND));
        survey.setName(req.getName());
        survey.setDescription(req.getDescription());
        survey.setEngineCode(req.getEngineCode());
        survey.setCategory(req.getCategory());
        survey.setQuestionTree(req.getQuestionTree());
        return dao.save(survey);
    }

    @Override
    public AutoTreeSurvey fetchOneAutoTreeSurvey(String id) throws CHException {
        return dao.findById(id).orElseThrow(() -> new CHException(CH_SURVEY_NOT_FOUND));
    }

    @Override
    public AutoTreeSurvey fetchOneByEngineAndCategory(String engineCode, String category) throws CHException {
        return dao.findByEngineCodeAndCategory(engineCode, category)
                .orElseThrow(() -> new CHException(CH_SURVEY_NOT_FOUND));
    }

    @Override
    public void deleteOneAutoTreeSurvey(String id) {
        dao.deleteById(id);
    }


    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

//    public QuestionNode search(QuestionNode root, int level, String sentiment)
//    {
//        // Base Cases: root is null or key is present at root
//        if (root==null || root.getLevel() == level)
//            return root;
//
//        // Invalid scenarios, level is smaller than root
//        if (root.getLevel() > level)
//            return null;
//
//        // Key is smaller than root's key
//        if (sentiment.equals("positive")){
//
//        }
//        return search(root.left, key);
//    }
}
