package com.longpc.devmon.portal.quizportal.service;

import com.longpc.devmon.portal.quizportal.entity.Field;

import java.util.List;
import java.util.Map;

/**
 * Long PC
 * 09/03/2024| 15:19 | 2024
 **/
public interface FieldService {
    List<Field> getAllField();
    Map<String,Field> getAllFieldMap();

    void create(String name, String performerId);

    void remove(String id, String performerId);

    void updateName(String id, String name, String performerId);

}
