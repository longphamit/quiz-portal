package com.longpc.devmon.portal.quizportal.entity.quiz;

import com.longpc.devmon.portal.quizportal.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Long PC
 * 18/10/24| 11:23 | 2024
 **/
@Setter
@Getter
public class Content extends BaseEntity {
    private String content;
    private String type;
    private String referId;
    private String referType;
    private int startGuideIndex;
    private int endGuideIndex;
}
