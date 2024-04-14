package com.longpc.devmon.portal.quizportal.entity.quiz.submit;

import com.longpc.devmon.portal.quizportal.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Long PC
 * 28/03/2024| 09:34 | 2024
 **/
@Getter
@Setter
@Document("question_answer_submit")
public class QuestionAnswerSubmit extends BaseEntity {
    private String questionTemplateId;
    private List<String> submitKeys;
}
