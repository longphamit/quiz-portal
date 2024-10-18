package com.longpc.devmon.portal.quizportal.gateway;

import com.longpc.devmon.portal.quizportal.entity.quiz.Content;
import com.longpc.devmon.portal.quizportal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Long PC
 * 18/10/24| 12:04 | 2024
 **/
@RestController
@RequestMapping("gateway/content")
@CrossOrigin("*")
public class ContentGateway {
    @Autowired
    private ContentService contentService;

    @PostMapping("/quiz-guide/referType/{referType}/referId/{referId}")
    public void createContent(@RequestHeader("PartyId") String partyId,
                              @PathVariable("referType") String referType,
                              @PathVariable("referId") String referId) {
        Content contentObject = new Content();
        contentObject.setReferId(referId);
        contentObject.setReferType(referType);
        contentObject.setType("QUIZ_GUIDE");
        contentObject.setStartGuideIndex(0);
        contentObject.setEndGuideIndex(0);
        contentService.createContent(contentObject, partyId);
    }

    @PutMapping
    public void updateContent(@RequestHeader("PartyId") String partyId,
                              @PathVariable("id") String id,
                              @PathVariable("content") String content) {
        contentService.updateContent(id, content, partyId);
    }

    @GetMapping("{id}")
    public Content getContent(@PathVariable String id) {
        return contentService.getById(id);
    }

    @GetMapping("/referType/{referType}/referId/{referId}")
    public List<Content> getContentsByReferId(
            @PathVariable String referId,
            @PathVariable String referType) {
        return contentService.getByReferTypeAndReferId(referType, referId);
    }
}
