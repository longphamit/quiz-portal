package com.longpc.devmon.portal.quizportal.service;

import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.Quiz;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Long PC
 * 04/03/2024| 21:35 | 2024
 **/

public interface QuizService {
    void createQuiz(Quiz quiz, String performerId);

    Page<Quiz> searchQuiz(String performerId);

    Quiz getById(String id);

    Quiz generateById(String id, String performerId);

    Quiz generateSurveyById(String id, String processType, String performerId);

    List<Quiz> getAll();

    void updateName(String id, String name, String performerId);

    void remove(String id, String performerId);

    void updateQuestionTemplates(String id, List<QuestionTemplate> questionTemplates, String performerId);

    void addQuestionTemplates(String id, List<QuestionTemplate> questionTemplates, String performerId);

    void updateFieldId(String id, String fieldId, String performerId);

    void removeQuestion(String id, String questionId, String performerId);

    void updateType(String id, TypeEnum.Quiz type, String performerId);

    void addQuizSubjectId(String id, String quizSubjectId, String performerId);

    boolean checkExistQuestionTemplate(String quizId, QuestionTemplate questionTemplate);

    boolean checkExistQuestionTemplate(String quizId, List<QuestionTemplate> questionTemplates);

    QuestionTemplate getQuestionTemplate(String quizId, String questionTemplateId);

    void updateGuide(String quizId, String guide, String performerId);

    void updateProcessType(String quizId, String processType, String performerId);

    void updateParticipantsLimit(String quizId, int participantsLimit, String performerId);

    void removeAllQuestionTemplate(String quizId);

    void generateQuestionForPairSurvey(String quizId, String performerId);

    List<Quiz> getByCreatedPartyId(String performerId);
}
