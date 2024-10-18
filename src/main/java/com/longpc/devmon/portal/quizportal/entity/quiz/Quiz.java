package com.longpc.devmon.portal.quizportal.entity.quiz;

import com.longpc.devmon.portal.quizportal.entity.BaseEntity;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Long PC
 * 04/03/2024| 21:00 | 2024
 **/
@Setter
@Getter
public class Quiz extends BaseEntity {
    private String name;
    private List<String> tagIds;
    private String startDate;
    private String endDate;
    private int questionSize;
    private String type;
    private String processType;
    private String fieldId;
    private List<QuestionTemplate> questionTemplates;
    private List<String> quizSubjectIds;
    private String guide;
    private int participantsLimit = 0;
    private String version;
    private List<String> guideIds;
}
