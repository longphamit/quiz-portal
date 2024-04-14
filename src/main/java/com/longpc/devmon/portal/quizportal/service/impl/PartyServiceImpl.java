package com.longpc.devmon.portal.quizportal.service.impl;

import com.longpc.devmon.portal.quizportal.entity.Party;
import com.longpc.devmon.portal.quizportal.manager.PartyManager;
import com.longpc.devmon.portal.quizportal.service.PartyService;
import com.longpc.devmon.portal.quizportal.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Long PC
 * 28/03/2024| 20:23 | 2024
 **/
@Service
public class PartyServiceImpl implements PartyService {
    @Autowired
    private PartyManager partyManager;

    @Override
    public Party create(String name, String performerId) {
        Party party = new Party();
        party.setId(DataUtil.generateId());
        party.setName(name);
       return (Party) partyManager.save(party, performerId);
    }

    public Party getById(String id) {
        return partyManager.getById(id);
    }

    public List<Party> getByIds(List<String> ids) {
        return partyManager.getByIds(ids);
    }
}
