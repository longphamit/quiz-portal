package com.longpc.devmon.portal.quizportal.view.model;

import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.service.QuestionTemplateService;
import com.longpc.devmon.portal.quizportal.util.SortUtil;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Long PC
 * 04/03/2024| 22:22 | 2024
 **/
@Component
public class QuestionTemplateLazyViewModel extends LazyDataModel<QuestionTemplate> {
    @Autowired
    private QuestionTemplateService questionTemplateService;

    @Override
    public int count(Map<String, FilterMeta> map) {
        return 0;
    }

    @Override
    public List<QuestionTemplate> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        int pageNumber = offset / pageSize;
        Page<QuestionTemplate> page = questionTemplateService.search(pageNumber, pageSize, SortUtil.getCreateTimeSort(), "");
        this.setRowCount(Long.valueOf(page.getTotalElements()).intValue());
        return page.toList();
    }
}
