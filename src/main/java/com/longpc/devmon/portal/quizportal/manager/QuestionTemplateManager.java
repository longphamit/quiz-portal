package com.longpc.devmon.portal.quizportal.manager;

import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.util.BaseSort;
import com.longpc.devmon.portal.quizportal.util.PageAbleUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * Long PC
 * 04/03/2024| 23:08 | 2024
 **/
@Component
public class QuestionTemplateManager extends BaseManager<QuestionTemplate> {
    public QuestionTemplateManager() {
        super("question_template", QuestionTemplate.class);
    }

    public void updateContent(String id, String content, String performerId) {
        updateAttribute(id, "content", content, performerId);
    }

    public void updateEncodeContent(String id, String content, String performerId) {
        updateAttribute(id, "encodeContent", content, performerId);
    }

    public void updateAnswerTemplateContent(String id, String answerId, String content, String performerId) {
        Query query = new Query(Criteria.where("_id").is(id).andOperator(Criteria.where("questionAnswerTemplates._id").is(answerId)));
        Update update = new Update();
        update.set("questionAnswerTemplates.$[answer].content", content);
        update.filterArray(Criteria.where("answer._id").is(answerId));
        mongoTemplate.updateFirst(query, update, this.collectionName);
    }

    public void updateType(String id, String type, String performerId) {
        updateAttribute(id, "type", type, performerId);
    }

    public Page<QuestionTemplate> search(int pageNumber, int pageSize, List<BaseSort> sorts, String performerId) {
        Pageable pageable = PageAbleUtil.convertPageableAndSort(
                pageNumber,
                pageSize,
                sorts);
        Query query = new Query();
        query.with(pageable);
        List<QuestionTemplate> questionTemplates = mongoTemplate.find(query, QuestionTemplate.class, this.collectionName);
        long countAll = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), QuestionTemplate.class, this.collectionName);
        return PageableExecutionUtils.getPage(questionTemplates, pageable, () -> countAll);
    }

    public List<QuestionTemplate> getRandomForAddToQuiz(int numLow, int numMedium, int numHigh) {
        List<QuestionTemplate> questionTemplates = new ArrayList<>();
        List<QuestionTemplate> lowLevel = getRandomByLevel(numLow, TypeEnum.QuestionLevel.LOW);
        List<QuestionTemplate> mediumLevel = getRandomByLevel(numMedium, TypeEnum.QuestionLevel.MEDIUM);
        List<QuestionTemplate> highLevel = getRandomByLevel(numHigh, TypeEnum.QuestionLevel.HIGH);
        if (!ObjectUtils.isEmpty(lowLevel)) {
            questionTemplates.addAll(lowLevel);
        }
        if (!ObjectUtils.isEmpty(mediumLevel)) {
            questionTemplates.addAll(mediumLevel);
        }
        if (!ObjectUtils.isEmpty(highLevel)) {
            questionTemplates.addAll(highLevel);
        }
        return questionTemplates;
    }

    private List<QuestionTemplate> getRandomByLevel(int limit, TypeEnum.QuestionLevel level) {
        if (limit != 0) {
            MatchOperation matchOperation = Aggregation.match(
                    Criteria.where("level").is(level.getCode()));
            SampleOperation sample = Aggregation.sample(limit);
            Aggregation aggregation = Aggregation.newAggregation(matchOperation, sample);
            return mongoTemplate.aggregate(aggregation, this.collectionName, QuestionTemplate.class).getMappedResults();
        }
        return new ArrayList<>();
    }

}
