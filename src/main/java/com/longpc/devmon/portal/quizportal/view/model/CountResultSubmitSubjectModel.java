package com.longpc.devmon.portal.quizportal.view.model;

import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Long PC
 * 29/03/2024| 19:09 | 2024
 **/
@Setter
@Getter
@Builder
public class CountResultSubmitSubjectModel {
    private QuizSubject quizSubject;
    private long count;
}
