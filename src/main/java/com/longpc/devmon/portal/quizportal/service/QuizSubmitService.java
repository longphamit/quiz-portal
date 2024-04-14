package com.longpc.devmon.portal.quizportal.service;

import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuizSubmit;
import com.longpc.devmon.portal.quizportal.exception.QuizSubmittedException;

import java.util.List;

/**
 * Long PC
 * 28/03/2024| 15:44 | 2024
 **/
public interface QuizSubmitService {
    List<QuizSubmit> generateSurveyTypePair(String quizId, List<QuestionTemplate> questionTemplates, String url);

    QuizSubmit getById(String id);

    void add(List<QuizSubmit> quizSubmits, String performerId);

    void submit(QuizSubmit quizSubmit);

    List<QuizSubmit> getByQuizId(String quizId);

    QuizSubmit getByIdToSubmit(String id) throws QuizSubmittedException;

    long countByQuizIdAndSubjectCodes(String quizId, List<String> subjectCodes);

    void remove(List<String> ids);
}
