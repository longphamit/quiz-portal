package com.longpc.devmon.portal.quizportal.service;

import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;

import java.util.List;

/**
 * Long PC
 * 27/03/2024| 22:45 | 2024
 **/
public interface QuizSubjectService {
    List<QuizSubject> getByIds(List<String> ids);

    QuizSubject create(String name, String key, String performerId);

    void updateCodes(String id, List<String> codes, String performerId);
}
