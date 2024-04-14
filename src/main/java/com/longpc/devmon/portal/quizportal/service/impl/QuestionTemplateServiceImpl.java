package com.longpc.devmon.portal.quizportal.service.impl;

import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuestionAnswerTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import com.longpc.devmon.portal.quizportal.manager.QuestionTemplateManager;
import com.longpc.devmon.portal.quizportal.service.QuestionTemplateService;
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
        List<QuizSubject> quizSubjectClones = new ArrayList<>();
        for (QuizSubject quizSubject : quizSubjects) {
            QuizSubject quizSubjectClone = new QuizSubject();
            quizSubjectClone.setKey(quizSubject.getKey());
            quizSubjectClone.setCodes(quizSubject.getCodes());
            quizSubjectClone.setId(quizSubject.getId());
            quizSubjectClones.add(quizSubjectClone);
        }
        if (isReverse) {
            Collections.reverse(quizSubjectClones);
        }

        int countNames = 0;
        if (processType.equals(TypeEnum.QuizProcessType.PAIR)) {
            countNames = 1;
        }
        if (processType.equals(TypeEnum.QuizProcessType.TRIANGLE)) {
            countNames = 1;
        }
        String foundName = new String();
        // tạo câu hỏi theo tổ hợp AB, AAB, ...
        for (int i = 0; i < countNames; i++) {
            StringBuilder name = new StringBuilder();
            List<String> subjectNames = quizSubjectClones.stream().map(e -> e.getKey()).collect(Collectors.toList());
            if (processType.equals(TypeEnum.QuizProcessType.PAIR)) {
                for (String subjectName : subjectNames) {
                    name.append(" [" + subjectName + "] ");
                }
                foundName = name.toString();
                names.put(name.toString(), name.toString());
            }
            if (processType.equals(TypeEnum.QuizProcessType.TRIANGLE)) {
                for (int f = 0; f < subjectNames.size() && ObjectUtils.isEmpty(foundName); f++) {
                    for (int j = 0; j < subjectNames.size() && ObjectUtils.isEmpty(foundName); j++) {
                        for (int k = 0; k < subjectNames.size() && ObjectUtils.isEmpty(foundName); k++) {
                            name = new StringBuilder();
                            if (subjectNames.get(f).equals(subjectNames.get(0))) {
                                if (subjectNames.get(f).equals(subjectNames.get(j)) && subjectNames.get(j).equals(subjectNames.get(k))) {
                                    continue;
                                }
                                name.append(" [" + subjectNames.get(f) + subjectNames.get(j) + subjectNames.get(k) + "] ");
                                foundName = name.toString();
                                names.put(name.toString(), name.toString());
                            }
                        }
                    }
                }
            }
        }
        List<QuestionTemplate> rs = new ArrayList<>();
        QuestionTemplate questionTemplate = new QuestionTemplate();
        questionTemplate.setId(id);
        questionTemplate.setContent(foundName);
        List<QuestionAnswerTemplate> answerTemplates = new ArrayList<>();
        // tạo câu tra lời cho câu hỏi
        if (processType.equals(TypeEnum.QuizProcessType.PAIR)) {
            for (int i = 0; i < 2; i++) {
                Collections.shuffle(quizSubjectClones.get(i).getCodes());
                QuestionAnswerTemplate questionAnswerTemplate = new QuestionAnswerTemplate();
                questionAnswerTemplate.setId(DataUtil.generateId());
                questionAnswerTemplate.setKey(quizSubjectClones.get(i).getCodes().get(0));
                questionAnswerTemplate.setQuizSubjectId(quizSubjectClones.get(i).getId());
                questionAnswerTemplate.setType(type.name());
                questionTemplate.setCreatedTime(new Date());
                questionTemplate.setCreatedBy(performerId);
                questionAnswerTemplate.setKeyLabel(quizSubjectClones.get(i).getCodes().get(0));
                answerTemplates.add(questionAnswerTemplate);
            }
            questionTemplate.setQuestionAnswerTemplates(answerTemplates);
        }
        if (processType.equals(TypeEnum.QuizProcessType.TRIANGLE)) {
            char[] chars = foundName.substring(2, foundName.length() - 2).toCharArray();
            for (int i = 0; i < chars.length; i++) {
                Map<String, QuizSubject> quizSubjectMap = new HashMap<>();
                for (QuizSubject quizSubject : quizSubjectClones) {
                    Collections.shuffle(quizSubject.getCodes());
                    quizSubjectMap.put(quizSubject.getKey(), quizSubject);
                }
                QuestionAnswerTemplate questionAnswerTemplate = new QuestionAnswerTemplate();
                questionAnswerTemplate.setId(DataUtil.generateId());
                List<String> quizSubjectCodes = quizSubjectMap.get("" + chars[i]).getCodes();
                String key = quizSubjectCodes.get(0);
                int flag = 0;
                // lấy mã khác những mã đã sử dụng
                for (int k = 0; k < quizSubjectCodes.size() && flag == 0; k++) {
                    if (!codes.containsKey(quizSubjectCodes.get(k))) {
                        key = quizSubjectCodes.get(k);
                        flag = 1;
                    }
                }
                questionAnswerTemplate.setKey(key);
                questionAnswerTemplate.setQuizSubjectId(quizSubjectMap.get("" + chars[i]).getId());
                questionAnswerTemplate.setType(type.name());
                questionTemplate.setCreatedTime(new Date());
                questionTemplate.setCreatedBy(performerId);
                questionAnswerTemplate.setKeyLabel(key);
                answerTemplates.add(questionAnswerTemplate);
            }
            questionTemplate.setQuestionAnswerTemplates(answerTemplates);
        }
        rs.add(questionTemplate);
        return rs;
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
