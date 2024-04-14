package com.longpc.devmon.portal.quizportal.gateway;

import com.longpc.devmon.portal.quizportal.entity.Party;
import com.longpc.devmon.portal.quizportal.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Long PC
 * 11/4/24| 22:34 | 2024
 **/
@RestController
@RequestMapping("gateway/party")
@CrossOrigin
public class PartyGateway {
    @Autowired
    private PartyService partyService;

    @GetMapping("{id}")
    public Party getById(@PathVariable("id") String id) {
        return partyService.getById(id);
    }
}
