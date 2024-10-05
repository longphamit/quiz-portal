package com.longpc.devmon.portal.quizportal.service.impl;

import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuestionAnswerTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import com.longpc.devmon.portal.quizportal.manager.QuestionTemplateManager;
import com.longpc.devmon.portal.quizportal.service.QuestionTemplateService;
import com.longpc.devmon.portal.quizportal.service.SurveyService;
import com.longpc.devmon.portal.quizportal.util.BaseSort;
import com.longpc.devmon.portal.quizportal.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Long PC
 * 04/03/2024| 22:26 | 2024
 **/
@Component
public class QuestionTemplateServiceImpl implements QuestionTemplateService {

    @Autowired
    public QuestionTemplateManager questionTemplateManager;
    @Autowired
    public SurveyService surveyService;

    @Override
    public QuestionTemplate getById(String id) {
        return questionTemplateManager.getById(id);
    }

    public QuestionTemplate generateById(String id, TypeEnum.Question type, String performerId) {
        QuestionTemplate questionTemplate = new QuestionTemplate();
        questionTemplate.setId(id);
        List<QuestionAnswerTemplate> answerTemplates = new ArrayList<>();
        if (type.equals(TypeEnum.Question.SELECT_ONE)) {
            for (int i = 1; i < 5; i++) {
                QuestionAnswerTemplate questionAnswerTemplate = new QuestionAnswerTemplate();
                questionAnswerTemplate.setId(DataUtil.generateId());
                questionAnswerTemplate.setKey(Integer.toString(i));
                questionAnswerTemplate.setType(type.name());
                questionTemplate.setCreatedTime(new Date());
                questionTemplate.setCreatedBy(performerId);
                questionAnswerTemplate.setKeyLabel(TypeEnum.lookUp_QuestionAnswerKey(i).name());
                answerTemplates.add(questionAnswerTemplate);
            }
            questionTemplate.setQuestionAnswerTemplates(answerTemplates);
        }
        return (QuestionTemplate) questionTemplateManager.save(questionTemplate, performerId);
    }

    public List<QuestionTemplate> generateById(String id,
                                               boolean isReverse,
                                               TypeEnum.Question type,
                                               TypeEnum.QuizProcessType processType,
                                               Map<String, String> codes,
                                               Map<String, String> names,
                                               List<QuizSubject> quizSubjects,
                                               String performerId) {
        if (processType.equals(TypeEnum.QuizProcessType.PAIR)) {
            return surveyService.generatePairById(id, isReverse, type, processType, codes, names, quizSubjects, performerId);
        }
        if (processType.equals(TypeEnum.QuizProcessType.TRIANGLE)) {
            return surveyService.generateTriangleById(id, isReverse, type, processType, codes, names, quizSubjects, performerId);
        }
        return new ArrayList<>();
    }


    @Override
    public void updateContent(String id, String content, String performerId) {
        questionTemplateManager.updateContent(id, content, performerId);
        String encodeContent = DataUtil.generateSlug(content);
        questionTemplateManager.updateEncodeContent(id, encodeContent, performerId);
    }

    @Override
    public void updateAnswerContent(String id, String answerId, String content, String performerId) {
        questionTemplateManager.updateAnswerTemplateContent(id, answerId, content, performerId);
    }


    public Page<QuestionTemplate> search(int pageNumber, int pageSize, List<BaseSort> sorts, String performerId) {
        return questionTemplateManager.search(pageNumber, pageSize, sorts, "");
    }

    public void remove(String id, String performerId) {
        questionTemplateManager.remove(id, performerId);
    }

    public void updateLevel(String id, int level, String performerId) {
        questionTemplateManager.updateLevel(id, level, performerId);
    }

    public void updateQuestionAnswerTemplateResultId(String id, String questionTemplateAnswerId, String performerId) {
        questionTemplateManager.updateQuestionAnswerTemplateResultId(id, questionTemplateAnswerId, performerId);
    }

    public List<QuestionTemplate> getRandomForAddToQuiz(int numEasy, int numMedium, int numHard) {
        return questionTemplateManager.getRandomForAddToQuiz(numEasy, numMedium, numHard);
    }
}
