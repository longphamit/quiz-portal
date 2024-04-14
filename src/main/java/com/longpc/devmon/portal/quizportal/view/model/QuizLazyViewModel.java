package com.longpc.devmon.portal.quizportal.view.model;

import com.longpc.devmon.portal.quizportal.entity.quiz.Quiz;
import com.longpc.devmon.portal.quizportal.service.QuizService;
import org.primefaces.model.FilterMeta;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Long PC
 * 04/03/2024| 20:59 | 2024
 **/
@Component
public class QuizLazyViewModel extends LazyDataModel<Quiz> {
    @Autowired
    QuizService quizService;

    @Override
    public int count(Map<String, FilterMeta> map) {
        return 0;
    }

    @Override
    public List<Quiz> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        List<Quiz> quizzes = quizService.getAll();
        Collections.reverse(quizzes);
        this.setRowCount(quizzes.size());
        return quizzes;
    }
}
