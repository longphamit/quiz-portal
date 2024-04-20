package com.longpc.devmon.portal.quizportal.gateway.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Long PC
 * 19/4/24| 17:19 | 2024
 **/
@Getter
@Setter
public class CountResultSubjectCodesPairModel {
    private String subjectId;
    private String subjectName;
    private String subjectKey;
    private long count;
}
