package com.longpc.devmon.portal.quizportal.entity.quiz;

import com.longpc.devmon.portal.quizportal.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Long PC
 * 27/03/2024| 22:27 | 2024
 **/
@Getter
@Setter
@Document("quiz_subject")
public class QuizSubject extends BaseEntity {
    private String key;
    private String name;
    private List<String> codes;
}
