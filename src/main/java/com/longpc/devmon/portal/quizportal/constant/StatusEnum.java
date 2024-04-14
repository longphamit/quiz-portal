package com.longpc.devmon.portal.quizportal.constant;

/**
 * Long PC
 * 04/03/2024| 21:17 | 2024
 **/
public class StatusEnum {
    public enum BaseStatus {
        ACTIVE(1),
        IN_ACTIVE(0);
        Integer code;

        BaseStatus(int code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

    public enum QuizStatus {
        NEW(1),
        DOING(2),
        DONE(3),
        CLOSE(4);
        Integer code;

        QuizStatus(int code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

}
