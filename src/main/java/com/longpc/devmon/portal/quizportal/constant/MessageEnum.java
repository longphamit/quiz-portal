package com.longpc.devmon.portal.quizportal.constant;

/**
 * Long PC
 * 05/03/2024| 12:47 | 2024
 **/
public class MessageEnum {
    public enum Activity {
        UPDATE_SUCCESS("Cập nhật thành công");
        String message;

        Activity(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }
}
