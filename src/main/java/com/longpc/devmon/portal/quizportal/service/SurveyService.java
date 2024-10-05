package com.longpc.devmon.portal.quizportal.service;

import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;

import java.util.List;
import java.util.Map;

/**
 * Long PC
 * 27/03/2024| 22:31 | 2024
 **/
public interface SurveyService {
    List<QuestionTemplate> generatePairById(String id,
                                            boolean isReverse,
                                            TypeEnum.Question type,
                                            TypeEnum.QuizProcessType processType,
                                            Map<String, String> codes,
                                            Map<String, String> names,
                                            List<QuizSubject> quizSubjects,
                                            String performerId);
    List<QuestionTemplate> generateTriangleById(String id,
                                                boolean isReverse,
                                                TypeEnum.Question type,
                                                TypeEnum.QuizProcessType processType,
                                                Map<String, String> codes,
                                                Map<String, String> names,
                                                List<QuizSubject> quizSubjects,
                                                String performerId);
}
