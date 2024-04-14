package com.longpc.devmon.portal.quizportal.exception;

import com.longpc.devmon.portal.quizportal.constant.ExceptionEnum;

/**
 * Long PC
 * 29/03/2024| 09:24 | 2024
 **/
public class QuizSubmittedException extends Exception {
    @Override
    public String getMessage() {
        return ExceptionEnum.QUIZ_SUBMITTED.name();
    }
}
