package com.longpc.devmon.portal.quizportal.service.impl;

import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import com.longpc.devmon.portal.quizportal.manager.QuizSubjectManager;
import com.longpc.devmon.portal.quizportal.service.QuizSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Long PC
 * 27/03/2024| 22:47 | 2024
 **/
@Service
public class QuizSubjectServiceImpl implements QuizSubjectService {
    @Autowired
    private QuizSubjectManager quizSubjectManager;

    @Override
    public List<QuizSubject> getByIds(List<String> quizId) {
        if (ObjectUtils.isEmpty(quizId)) {
            return new ArrayList<>();
        }
        return quizSubjectManager.getByIds(quizId);
    }

    @Override
    public QuizSubject create(String name, String key, String performerId) {
        QuizSubject quizSubject = new QuizSubject();
        quizSubject.setName(name);
        quizSubject.setKey(key);
        return (QuizSubject) quizSubjectManager.save(quizSubject, performerId);
    }

    @Override
    public void updateCodes(String id, List<String> codes, String performerId) {
        quizSubjectManager.updateAttribute(id, "codes", codes, performerId);
    }
}
