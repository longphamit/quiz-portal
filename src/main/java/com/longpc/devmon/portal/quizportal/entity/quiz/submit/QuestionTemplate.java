package com.longpc.devmon.portal.quizportal.entity.quiz.submit;

import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.BaseEntity;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuestionAnswerTemplate;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Long PC
 * 04/03/2024| 21:08 | 2024
 **/
@Getter
@Setter
public class QuestionTemplate extends BaseEntity {
    private String content;
    private String encodeContent;
    private List<QuestionAnswerTemplate> questionAnswerTemplates;
    private String quizCategoryId;
    private String type;
    private Integer level = TypeEnum.QuestionLevel.MEDIUM.getCode();
    private String subjectId;
    private String questionAnswerTemplateResultId;
    private String source;

}
