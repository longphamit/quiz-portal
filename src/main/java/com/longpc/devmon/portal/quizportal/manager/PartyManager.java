package com.longpc.devmon.portal.quizportal.manager;

import com.longpc.devmon.portal.quizportal.entity.Party;
import org.springframework.stereotype.Repository;

/**
 * Long PC
 * 28/03/2024| 20:22 | 2024
 **/
@Repository
public class PartyManager extends BaseManager<Party> {
    public PartyManager() {
        super("party", Party.class);
    }
}
