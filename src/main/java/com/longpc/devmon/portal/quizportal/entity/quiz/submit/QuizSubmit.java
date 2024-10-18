package com.longpc.devmon.portal.quizportal.entity.quiz.submit;

import com.longpc.devmon.portal.quizportal.entity.BaseEntity;
import com.longpc.devmon.portal.quizportal.entity.quiz.QR;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionAnswerSubmit;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Long PC
 * 27/03/2024| 22:22 | 2024
 **/
@Getter
@Setter
@Document("quiz_submit")
public class QuizSubmit extends BaseEntity {
    private String quizId;
    private String submitPartyId;
    private Date submitTime;
    private List<QuestionAnswerSubmit> questionAnswerSubmits;
    private QR qr;
    private String guideId;
    private int status;
    private int index;
}
