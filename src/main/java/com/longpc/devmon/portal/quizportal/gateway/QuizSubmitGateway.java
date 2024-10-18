package com.longpc.devmon.portal.quizportal.gateway;

import com.longpc.devmon.portal.quizportal.entity.Party;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuizSubmit;
import com.longpc.devmon.portal.quizportal.exception.QuizSubmittedException;
import com.longpc.devmon.portal.quizportal.gateway.model.ExceptionModel;
import com.longpc.devmon.portal.quizportal.gateway.model.SubmitQuizGWModel;
import com.longpc.devmon.portal.quizportal.service.PartyService;
import com.longpc.devmon.portal.quizportal.service.QuizSubmitService;
import com.longpc.devmon.portal.quizportal.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Long PC
 * 28/03/2024| 20:16 | 2024
 **/
@RestController
@RequestMapping("gateway/quiz-submit")
@CrossOrigin("*")
public class QuizSubmitGateway {
    @Autowired
    QuizSubmitService quizSubmitService;

    @Autowired
    PartyService partyService;

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(quizSubmitService.getByIdToSubmit(id));
        } catch (QuizSubmittedException quizSubmittedException) {
            return ResponseEntity.badRequest().body(ExceptionModel.builder().exception(quizSubmittedException.getMessage()).build());
        }
    }

    @GetMapping("{id}/result")
    public ResponseEntity getViewResult(@PathVariable("id") String id) {
        return ResponseEntity.ok(quizSubmitService.getById(id));
    }

    @PutMapping("submit")
    public QuizSubmit get(@RequestBody SubmitQuizGWModel submitQuizGWModel) {
        Party party = partyService.create(submitQuizGWModel.getPartyName(), SessionUtil.getSystemId());
        QuizSubmit quizSubmit = quizSubmitService.getById(submitQuizGWModel.getQuizSubmitId());
        quizSubmit.setQuestionAnswerSubmits(submitQuizGWModel.getQuestions());
        quizSubmit.setSubmitPartyId(party.getId());
        quizSubmitService.submit(quizSubmit);
        return quizSubmitService.getById(quizSubmit.getId());
    }


}
