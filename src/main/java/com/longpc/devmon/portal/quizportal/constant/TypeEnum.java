package com.longpc.devmon.portal.quizportal.constant;

/**
 * Long PC
 * 04/03/2024| 21:15 | 2024
 **/
public class TypeEnum {
    public enum QuestionAnswerKey {
        A(1),
        B(2),
        C(3),
        D(4);
        Integer code;

        QuestionAnswerKey(int code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

    public enum Quiz {
        SURVEY,
        TEST
    }

    public enum Question {
        SELECT_MANY,
        SELECT_ONE,
        TEXT,
        RADIO
    }

    public static QuestionAnswerKey lookUp_QuestionAnswerKey(Integer code) {
        switch (code) {
            case 1:
                return QuestionAnswerKey.A;
            case 2:
                return QuestionAnswerKey.B;
            case 3:
                return QuestionAnswerKey.C;
            case 4:
                return QuestionAnswerKey.D;
        }
        return null;
    }

    public enum QuestionLevel {
        HIGH(3),
        MEDIUM(2),
        LOW(1);

        private int code;

        QuestionLevel(int code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

    public enum QuestionSource {
        SURVEY,
        TEST
    }

    public enum QuizProcessType {
        PAIR,
        TRIANGLE
    }
}
