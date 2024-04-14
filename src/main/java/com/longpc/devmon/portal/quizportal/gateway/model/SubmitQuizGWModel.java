package com.longpc.devmon.portal.quizportal.gateway.model;

import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionAnswerSubmit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Long PC
 * 29/03/2024| 00:30 | 2024
 **/
@Getter
@Setter
public class SubmitQuizGWModel {
    private String partyName;
    private String quizSubmitId;
    private List<QuestionAnswerSubmit> questions;
}
