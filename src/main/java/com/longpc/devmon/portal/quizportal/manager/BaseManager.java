package com.longpc.devmon.portal.quizportal.manager;

import com.longpc.devmon.portal.quizportal.entity.BaseEntity;
import com.longpc.devmon.portal.quizportal.util.DataUtil;
import com.mongodb.client.result.UpdateResult;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * Long PC
 * 04/03/2024| 23:08 | 2024
 **/
public abstract class BaseManager<T> {
    @Autowired
    protected MongoTemplate mongoTemplate;
    protected String collectionName;
    protected Class<T> tClass;

    public BaseManager(String collectionName, Class<T> tClass) {
        this.collectionName = collectionName;
        this.tClass = tClass;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public Object save(Object o, String performerId) {
        if (o instanceof BaseEntity) {
            BaseEntity baseModel = (BaseEntity) o;
            if (ObjectUtils.isEmpty(baseModel.getId())) {
                baseModel.setId(DataUtil.generateId());
            }
            if (null == baseModel.getCreatedTime()) {
                baseModel.setCreatedTime(new Date());
                baseModel.setCreatedBy(performerId);
            }
        }
        return mongoTemplate.save(o, collectionName);
    }

    public T getById(String id) {
        return this.mongoTemplate.findById(id, this.tClass, this.collectionName);
    }

    public List<T> getByIds(List<String> ids) {
        return this.mongoTemplate.find(Query.query(Criteria.where("_id").in(ids)), this.tClass, this.collectionName);
    }

    public List<T> getAll() {
        return this.mongoTemplate.findAll(this.tClass);
    }

    public void updateAttribute(String id, String name, Object value, String performerId) {
        Update update = new Update();
        update.set(name, value);
        this.mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), update, this.collectionName);
    }

    public void remove(String id, String performerId) {
        this.mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), this.collectionName);
    }

    public void updateLevel(String id, int level, String performerId) {
        this.updateAttribute(id, "level", level, performerId);
    }

    public void updateQuestionAnswerTemplateResultId(String id, String questionTemplateAnswerId, String performerId) {
        this.updateAttribute(id, "questionAnswerTemplateResultId", questionTemplateAnswerId, performerId);
    }
}