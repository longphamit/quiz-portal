package com.longpc.devmon.portal.quizportal.gateway;

import com.longpc.devmon.portal.quizportal.entity.quiz.Quiz;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Long PC
 * 25/03/2024| 14:54 | 2024
 **/
@RestController
@RequestMapping("gateway/quiz")
@CrossOrigin
public class QuizGateway {
    @Autowired
    QuizService quizService;

    @GetMapping("{id}")
    public Quiz get(@PathVariable("id") String id) {
        return quizService.getById(id);
    }

    @GetMapping("{id}/question-template/{questionId}")
    public QuestionTemplate getQuestionTemplate(
            @PathVariable("id") String id,
            @PathVariable("questionId") String questionId) {
        return quizService.getQuestionTemplate(id, questionId);
    }
}
