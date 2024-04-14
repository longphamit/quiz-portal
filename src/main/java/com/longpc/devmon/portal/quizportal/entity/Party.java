package com.longpc.devmon.portal.quizportal.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Long PC
 * 04/03/2024| 21:23 | 2024
 **/
@Setter
@Getter
@Document("party")
public class Party extends BaseEntity{
    private String name;
}
