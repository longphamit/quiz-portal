package com.longpc.devmon.portal.quizportal.view;

import com.longpc.devmon.portal.quizportal.config.scope.view.ViewScope;
import com.longpc.devmon.portal.quizportal.entity.Field;
import com.longpc.devmon.portal.quizportal.service.FieldService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Long PC
 * 09/03/2024| 15:17 | 2024
 **/
@Getter
@Setter
@Component("FieldView")
@ViewScope
public class FieldView extends BaseView {
    private List<Field> fields;
    @Autowired
    private FieldService fieldService;
    private String fieldAddName;

    public void onLoad() {
        if (checkPostBack()) {
            fields = fieldService.getAllField();
        }
    }

    public void remove(String id) {
        fieldService.remove(id, "");
        fields = fieldService.getAllField();
    }

    public void add() {
        fieldService.create(fieldAddName, "");
        fields = fieldService.getAllField();
        fieldAddName = new String();
        hideDialog("wgvAddField");
    }
}
