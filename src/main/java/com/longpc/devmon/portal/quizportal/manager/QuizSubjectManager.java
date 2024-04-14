package com.longpc.devmon.portal.quizportal.manager;

import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Long PC
 * 27/03/2024| 22:47 | 2024
 **/
@Repository
public class QuizSubjectManager extends BaseManager<QuizSubject> {

    public QuizSubjectManager() {
        super("quiz_subject", QuizSubject.class);
    }

    public List<QuizSubject> getByIds(List<String> ids) {
        return mongoTemplate.find(Query.query(Criteria.where("_id").in(ids)), QuizSubject.class);
    }
}
