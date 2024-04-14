package com.longpc.devmon.portal.quizportal.service.impl;

import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.Quiz;
import com.longpc.devmon.portal.quizportal.manager.QuizManager;
import com.longpc.devmon.portal.quizportal.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Long PC
 * 04/03/2024| 21:40 | 2024
 **/
@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizManager quizManager;

    @Override
    public void createQuiz(Quiz quiz, String perfomerId) {

    }

    @Override
    public Page<Quiz> searchQuiz(String performerId) {
        return null;
    }

    @Override
    public Quiz getById(String id) {
        return quizManager.getById(id);
    }

    @Override
    public Quiz generateById(String id, String performerId) {
        Quiz quiz = new Quiz();
        quiz.setId(id);
        quiz.setType(TypeEnum.Quiz.TEST.name());
        return (Quiz) quizManager.save(quiz, performerId);
    }

    @Override
    public List<Quiz> getAll() {
        return quizManager.getAll();
    }

    @Override
    public void updateName(String id, String name, String performerId) {
        quizManager.updateAttribute(id, "name", name, performerId);
    }

    @Override
    public void remove(String id, String performerId) {
        quizManager.remove(id, performerId);
    }

    @Override
    public void updateQuestionTemplates(String id, List<QuestionTemplate> questionTemplates, String performerId) {
        quizManager.updateAttribute(id, "questionTemplates", questionTemplates, performerId);
    }

    @Override
    public void addQuestionTemplates(String id, List<QuestionTemplate> questionTemplates, String performerId) {
        quizManager.addQuestionTemplate(id, questionTemplates, performerId);
    }

    @Override
    public void updateFieldId(String id, String fieldId, String performerId) {
        quizManager.updateAttribute(id, "fieldId", fieldId, performerId);
    }

    @Override
    public void removeQuestion(String id, String questionId, String performerId) {
        quizManager.removeQuestionTemplate(id, questionId, performerId);
    }

    @Override
    public void updateType(String id, TypeEnum.Quiz type, String performerId) {
        quizManager.updateAttribute(id, "type", type.name(), performerId);
    }

    @Override
    public void addQuizSubjectId(String id, String quizSubjectId, String performerId) {
        List<String> quizSubjectIds = new ArrayList<>();
        quizSubjectIds.add(quizSubjectId);
        quizManager.addQuizSubjectIds(id, quizSubjectIds, performerId);
    }

    @Override
    public boolean checkExistQuestionTemplate(String quizId, QuestionTemplate questionTemplate) {
        return quizManager.checkExistQuestionTemplate(quizId, questionTemplate);
    }

    @Override
    public boolean checkExistQuestionTemplate(String quizId, List<QuestionTemplate> questionTemplates) {
        return quizManager.checkExistQuestionTemplate(quizId, questionTemplates);
    }


    @Override
    public QuestionTemplate getQuestionTemplate(String quizId, String questionTemplateId) {
        return quizManager.getQuestionTemplate(quizId, questionTemplateId);
    }

    @Override
    public void updateGuide(String quizId, String guide, String performerId) {
        quizManager.updateAttribute(quizId, "guide", guide, performerId);
    }

    @Override
    public void updateProcessType(String quizId, String processType, String performerId) {
        quizManager.updateAttribute(quizId, "processType", processType, performerId);
    }

    @Override
    public void updateParticipantsLimit(String quizId, int participantsLimit, String performerId) {
        quizManager.updateAttribute(quizId, "participantsLimit", participantsLimit, performerId);
    }

    @Override
    public void removeAllQuestionTemplate(String quizId) {
        quizManager.removeAllQuestionTemplate(quizId);
    }
}
