package com.longpc.devmon.portal.quizportal.manager;

import com.longpc.devmon.portal.quizportal.entity.Field;
import com.longpc.devmon.portal.quizportal.entity.quiz.Quiz;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Long PC
 * 04/03/2024| 21:41 | 2024
 **/
@Repository
public class QuizManager extends BaseManager<Quiz> {

    public QuizManager() {
        super("quiz", Quiz.class);
    }

    public void removeQuestionTemplate(String id, String questionTemplateId, String performerId) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.pull("questionTemplates", new Query(Criteria.where("_id").is(questionTemplateId)));
        mongoTemplate.updateFirst(query, update, this.collectionName);
    }
    public void removeAllQuestionTemplate(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.unset("questionTemplates");
        mongoTemplate.updateFirst(query, update, this.collectionName);
    }

    public void addQuizSubjectIds(String id, List<String> quizSubjectIds, String performerId) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        for (String quizSubjectId : quizSubjectIds) {
            update.push("quizSubjectIds", quizSubjectId);
        }

        mongoTemplate.updateFirst(query, update, this.collectionName);
    }

    public void addQuestionTemplate(String id, List<QuestionTemplate> questionTemplates, String performerId) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        for (QuestionTemplate questionTemplate : questionTemplates) {
            update.push("questionTemplates", questionTemplate);
        }
        mongoTemplate.updateFirst(query, update, this.collectionName);
    }

    public boolean checkExistQuestionTemplate(String quizId, QuestionTemplate questionTemplate) {
        List<String> keys = questionTemplate.getQuestionAnswerTemplates().stream().map(e -> e.getKey()).collect(Collectors.toList());
        return mongoTemplate.exists(Query.query(Criteria.where("_id").is(quizId)
                .andOperator(Criteria.where("questionTemplates.questionAnswerTemplates.key").in(keys))), Quiz.class);
    }

    public boolean checkExistQuestionTemplate(String quizId, List<QuestionTemplate> questionTemplate) {
        List<String> allKeys = new ArrayList<>();
        for (QuestionTemplate template : questionTemplate) {
            List<String> keys = template.getQuestionAnswerTemplates().stream().map(e -> e.getKey()).collect(Collectors.toList());
            allKeys.addAll(keys);
        }
        return mongoTemplate.exists(Query.query(Criteria.where("_id").is(quizId)
                .andOperator(Criteria.where("questionTemplates.questionAnswerTemplates.key").in(allKeys))), Quiz.class);
    }

    public QuestionTemplate getQuestionTemplate(String quizId, String questionTemplateId) {
        Query query = Query.query(Criteria.where("_id").is(quizId)
                .andOperator(Criteria.where("questionTemplates._id").is(questionTemplateId)));
        List<QuestionTemplate> questionTemplates = mongoTemplate.findDistinct(query,
                "questionTemplates",
                this.collectionName,
                QuestionTemplate.class);
        if (!ObjectUtils.isEmpty(questionTemplates)) {
            for (QuestionTemplate questionTemplate : questionTemplates) {
                if (questionTemplate.getId().equals(questionTemplateId)) {
                    return questionTemplate;
                }
            }
        }
        return null;
    }
}
