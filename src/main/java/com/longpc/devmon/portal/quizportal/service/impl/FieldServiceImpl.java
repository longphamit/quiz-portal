package com.longpc.devmon.portal.quizportal.service.impl;

import com.longpc.devmon.portal.quizportal.entity.Field;
import com.longpc.devmon.portal.quizportal.manager.FieldManager;
import com.longpc.devmon.portal.quizportal.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Long PC
 * 09/03/2024| 15:21 | 2024
 **/
@Component
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldManager fieldManager;

    @Override
    public List<Field> getAllField() {
        return fieldManager.getAll();
    }

    public Map<String, Field> getAllFieldMap() {
        Map<String, Field> map = new HashMap<>();
        List<Field> fields = fieldManager.getAll();
        if (!ObjectUtils.isEmpty(fields)) {
            for (Field field : fields) {
                map.put(field.getId(), field);
            }
        }
        return map;
    }

    @Override
    public void create(String name, String performerId) {
        Field field = new Field();
        field.setName(name);
        fieldManager.save(field, performerId);
    }

    @Override
    public void remove(String id, String performerId) {
        fieldManager.remove(id, performerId);
    }

    @Override
    public void updateName(String id, String name, String performerId) {
        fieldManager.updateAttribute(id, "name", name, performerId);
    }
}
