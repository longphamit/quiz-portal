package com.longpc.devmon.portal.quizportal.gateway.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Long PC
 * 18/4/24| 22:55 | 2024
 **/
@Getter
@Setter
public class UpdateQuizBaseData {
    private String name;
    private String guide;
    private int participantsLimit;
}
