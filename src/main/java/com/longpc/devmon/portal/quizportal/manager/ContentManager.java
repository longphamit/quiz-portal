package com.longpc.devmon.portal.quizportal.manager;

import com.longpc.devmon.portal.quizportal.entity.Field;
import com.longpc.devmon.portal.quizportal.entity.quiz.Content;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Long PC
 * 18/10/24| 12:05 | 2024
 **/
@Component
public class ContentManager extends BaseManager {
    public ContentManager() {
        super("content", ContentManager.class);
    }

    public List<Content> getByReferTypeAndReferId(String referType, String referId) {
        return mongoTemplate.find(Query.query(Criteria.where("referType").is(referType).and("referId").is(referId)), Content.class, this.collectionName);
    }

}
