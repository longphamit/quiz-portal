package com.longpc.devmon.portal.quizportal.service;

import com.longpc.devmon.portal.quizportal.entity.Party;

import java.util.List;

/**
 * Long PC
 * 28/03/2024| 20:23 | 2024
 **/
public interface PartyService {
    Party create(String name, String performerId);

    Party getById(String id);

    List<Party> getByIds(List<String> ids);

    List<Party> getAll();
}
