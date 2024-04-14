package com.longpc.devmon.portal.quizportal.view;

import com.longpc.devmon.portal.quizportal.config.scope.view.ViewScope;
import com.longpc.devmon.portal.quizportal.entity.Field;
import com.longpc.devmon.portal.quizportal.service.FieldService;
import com.longpc.devmon.portal.quizportal.util.DataUtil;
import com.longpc.devmon.portal.quizportal.view.model.QuizLazyViewModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Long PC
 * 04/03/2024| 20:59 | 2024
 **/
@Component("QuizView")
@ViewScope
@Getter
@Setter
public class QuizView extends BaseView {
    @Autowired
    private QuizLazyViewModel quizLazyViewModel;
    @Autowired
    private FieldService fieldService;
    private Map<String, Field> fields = new HashMap<>();

    public void onLoad() {
        if (checkPostBack()) {
            fields = fieldService.getAllFieldMap();
        }
    }

    public void addQuiz() {
        String id = DataUtil.generateId();
        redirectPageInNewTab("/pages/quiz/detail.xhtml?id=" + id + "&mode=" + DetailPageMode.CREATE.name());
    }

    public void viewDetail(String id) {
        redirectPageInNewTab("/pages/quiz/detail.xhtml?id=" + id + "&mode=" + DetailPageMode.VIEW.name());
    }
}
