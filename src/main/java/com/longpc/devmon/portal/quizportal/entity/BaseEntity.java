package com.longpc.devmon.portal.quizportal.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Long PC
 * 04/03/2024| 21:09 | 2024
 **/
@Getter
@Setter
public class BaseEntity {
    private String id;
    private Date createdTime;
    private String createdBy;
}
