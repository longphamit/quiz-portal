package com.longpc.devmon.portal.quizportal.gateway;

import com.longpc.devmon.portal.quizportal.entity.quiz.Content;
import com.longpc.devmon.portal.quizportal.gateway.model.UpdateQuizContentData;
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

    @PostMapping("/quiz-guide/{quizId}")
    public void createContent(@PathVariable("quizId") String quizId) {
        Content contentObject = new Content();
        contentObject.setReferId(quizId);
        contentObject.setReferType("QUIZ");
        contentObject.setType("QUIZ_GUIDE");
        contentObject.setStartGuideIndex(0);
        contentObject.setEndGuideIndex(0);
        contentService.createContent(contentObject, "");
    }

    @PutMapping("{id}/start-guide-index/{startGuideIndex}")
    public void updateStartGuideIndex(@PathVariable("id") String id,
                                      @PathVariable("startGuideIndex") int startGuideIndex) {
        contentService.updateStartGuideIndex(id, startGuideIndex, "");
    }

    @PutMapping("{id}/end-guide-index/{endGuideIndex}")
    public void updateEndGuideIndex(@PathVariable("id") String id,
                                    @PathVariable("endGuideIndex") int endGuideIndex) {
        contentService.updateEndGuideIndex(id, endGuideIndex, "");
    }

    @GetMapping("/quiz-guide/{quizId}/index/{index}")
    public Content getByQuizIdAndGuideIndex(@PathVariable("quizId") String quizId,
                                            @PathVariable("index") Integer index) {
        return contentService.geByQuizIdAndGuideIndex(quizId, index);
    }

    @PutMapping("{id}/content")
    public void updateContent(
            @PathVariable("id") String id,
            @RequestBody UpdateQuizContentData content) {
        contentService.updateContent(id, content.getContent(), "");
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

    @GetMapping("{id}/delete")
    public void delete(@PathVariable String id) {
        contentService.deleteContent(id);
    }
}
