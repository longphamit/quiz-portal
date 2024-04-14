package com.longpc.devmon.portal.quizportal.service;

import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import com.longpc.devmon.portal.quizportal.util.BaseSort;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Long PC
 * 04/03/2024| 22:25 | 2024
 **/
public interface QuestionTemplateService {
    QuestionTemplate getById(String id);

    QuestionTemplate generateById(String id, TypeEnum.Question type, String performerId);

    List<QuestionTemplate> generateById(String id,
                                        boolean isReverse,
                                        TypeEnum.Question type,
                                        TypeEnum.QuizProcessType processType,
                                        Map<String, String> codes,
                                        Map<String, String> names,
                                        List<QuizSubject> quizSubjects,
                                        String performerId);

    void updateContent(String id, String content, String performerId);

    void updateAnswerContent(String id, String answerId, String content, String performerId);

    Page<QuestionTemplate> search(int pageNumber, int pageSize, List<BaseSort> sorts, String performerId);

    void remove(String id, String performerId);

    void updateLevel(String id, int level, String performerId);

    void updateQuestionAnswerTemplateResultId(String id, String questionTemplateAnswerId, String performerId);

    List<QuestionTemplate> getRandomForAddToQuiz(int numEasy, int numMedium, int numHard);
}
