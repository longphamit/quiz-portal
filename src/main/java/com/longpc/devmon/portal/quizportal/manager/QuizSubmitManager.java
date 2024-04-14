package com.longpc.devmon.portal.quizportal.manager;

import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuizSubmit;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Long PC
 * 28/03/2024| 20:17 | 2024
 **/
@Repository
public class QuizSubmitManager extends BaseManager<QuizSubmit> {
    public QuizSubmitManager() {
        super("quiz_submit", QuizSubmit.class);
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<QuizSubmit> getByQuizId(String quizId) {
        return mongoTemplate.find(Query.query(Criteria.where("quizId").is(quizId)), QuizSubmit.class);
    }

    public long countByQuizIdAndSubjectCodes(String quizId, List<String> subjectCodes) {
        if (ObjectUtils.isEmpty(subjectCodes)) {
            return 0;
        }
        return mongoTemplate.count(Query.query(Criteria.where("quizId").is(quizId)
                .andOperator(
                        Criteria.where("questionAnswerSubmits.submitKeys").in(subjectCodes)
                )), QuizSubmit.class);
    }

    public void remove(List<String> ids) {
        mongoTemplate.remove(Query.query(Criteria.where("id").in(ids)), QuizSubmit.class);
    }
}
