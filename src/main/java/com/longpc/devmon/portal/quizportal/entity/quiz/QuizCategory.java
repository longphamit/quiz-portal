package com.longpc.devmon.portal.quizportal.entity.quiz;

import com.longpc.devmon.portal.quizportal.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Long PC
 * 04/03/2024| 21:20 | 2024
 **/
@Getter
@Setter
public class QuizCategory extends BaseEntity {
    private String name;
    private String quizId;
    private String startDate;
    private String endDate;
}
