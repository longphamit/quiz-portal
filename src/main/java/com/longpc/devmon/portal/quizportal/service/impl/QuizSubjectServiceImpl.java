package com.longpc.devmon.portal.quizportal.service.impl;

import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.quiz.Quiz;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import com.longpc.devmon.portal.quizportal.manager.QuizManager;
import com.longpc.devmon.portal.quizportal.manager.QuizSubjectManager;
import com.longpc.devmon.portal.quizportal.service.QuizSubjectService;
import com.longpc.devmon.portal.quizportal.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Long PC
 * 27/03/2024| 22:47 | 2024
 **/
@Service
public class QuizSubjectServiceImpl implements QuizSubjectService {
    @Autowired
    private QuizSubjectManager quizSubjectManager;
    @Autowired
    private QuizManager quizManager;

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

    @Override
    public void generateCode(String quizSubjectId, String quizId, String performerId) {
        int countCodes = 0;
        Quiz quiz = quizManager.getById(quizId);
        List<QuizSubject> quizSubjects = getByIds(quiz.getQuizSubjectIds());
        if (quiz.getProcessType().equals(TypeEnum.QuizProcessType.PAIR.name())) {
            countCodes = quiz.getParticipantsLimit();
        } else if (quiz.getProcessType().equals(TypeEnum.QuizProcessType.TRIANGLE.name())) {
            countCodes = quiz.getParticipantsLimit() + quiz.getParticipantsLimit() / 2;
        }
        List<String> existCode = new ArrayList<>();
        for (QuizSubject quizSubject : quizSubjects) {
            if (!ObjectUtils.isEmpty(quizSubject.getCodes()) && !quizSubject.getId().equals(quizSubjectId)) {
                existCode.addAll(quizSubject.getCodes());
            }
        }
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < countCodes; i++) {
            List<Integer> digits = new ArrayList<>();
            for (int j = 0; j <= 9; j++) {
                digits.add(j);
            }

            boolean checkFails = false;
            String code = new String();
            do {
                Collections.shuffle(digits);
                List<Integer> uniqueDigits = digits.subList(0, 3);
                StringBuilder rs = new StringBuilder();
                for (Integer integer : uniqueDigits) {
                    rs.append(integer);
                }
                code = rs.toString();


                if (code.startsWith("0")) {
                    checkFails = true;
                    System.out.println(" ==== CHECK CODE START 0 ==== " + code);
                } else if (existCode.contains(code) || codes.contains(code)) {
                    System.out.println(" ==== CHECK CODE EXIST ==== " + code);
                    checkFails = true;
                } else if ((uniqueDigits.get(1) == uniqueDigits.get(0) + 1) && (uniqueDigits.get(2) == uniqueDigits.get(1) + 1)) {
                    System.out.println(" ==== CHECK CODE 123 ==== " + code);
                    checkFails = true;
                } else if ((uniqueDigits.get(0) + uniqueDigits.get(1) + uniqueDigits.get(2)) % 3 != 0) {
                    checkFails = true;
                    System.out.println(" ==== CHECK % 3 != 0 ==== " + code);
                } else {
                    checkFails = false;
                }
            } while (checkFails);
            if (!ObjectUtils.isEmpty(code)) {
                codes.add(code);
            }
        }
        updateCodes(quizSubjectId, codes, SessionUtil.getLoginId());
    }
}
