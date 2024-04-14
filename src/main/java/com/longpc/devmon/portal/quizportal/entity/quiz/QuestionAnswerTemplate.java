package com.longpc.devmon.portal.quizportal.entity.quiz;

import com.longpc.devmon.portal.quizportal.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Long PC
 * 04/03/2024| 21:12 | 2024
 **/
@Getter
@Setter
public class QuestionAnswerTemplate extends BaseEntity {
    private String content;
    private String key;
    private String keyLabel;
    private String type;
    private String quizSubjectId;
}
