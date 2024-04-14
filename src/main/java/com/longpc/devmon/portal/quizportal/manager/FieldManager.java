package com.longpc.devmon.portal.quizportal.manager;

import com.longpc.devmon.portal.quizportal.entity.Field;
import org.springframework.stereotype.Component;

/**
 * Long PC
 * 09/03/2024| 15:17 | 2024
 **/
@Component
public class FieldManager extends BaseManager<Field> {
    public FieldManager() {
        super("field", Field.class);
    }

}
