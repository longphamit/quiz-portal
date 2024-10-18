package com.longpc.devmon.portal.quizportal.service;

import com.longpc.devmon.portal.quizportal.entity.quiz.Content;
import com.longpc.devmon.portal.quizportal.manager.ContentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Long PC
 * 18/10/24| 12:06 | 2024
 **/
@Service
public class ContentService {

    @Autowired
    public ContentManager contentManager;

    public Content getById(String id) {
        return (Content) contentManager.getById(id);
    }

    public Content createContent(Content content, String createdBy) {
        return (Content) contentManager.save(content, createdBy);
    }

    public void updateContent(String id, String content, String updatedBy) {
        contentManager.updateAttribute(id, "content", content, updatedBy);
    }

    public List<Content> getByReferTypeAndReferId(String referType, String referId) {
        return contentManager.getByReferTypeAndReferId(referType, referId);
    }

    public Content geByQuizIdAndGuideIndex(String quizId, int guideIndex) {
        List<Content> contents = contentManager.getByReferTypeAndReferId("QUIZ", quizId);
        int flag = 0;
        Content rs = null;
        for (int i = 0; i < contents.size() && flag == 0; i++) {
            if (contents.get(i).getStartGuideIndex() <= guideIndex && contents.get(i).getEndGuideIndex() >= guideIndex) {
                flag = 1;
                rs = contents.get(i);
            }
        }
        return rs;
    }
    public void updateStartGuideIndex(String id, int startGuideIndex, String updatedBy) {
        contentManager.updateAttribute(id, "startGuideIndex", startGuideIndex, updatedBy);
    }
    public void updateEndGuideIndex(String id, int endGuideIndex, String updatedBy) {
        contentManager.updateAttribute(id, "endGuideIndex", endGuideIndex, updatedBy);
    }
    public void deleteContent(String id) {
        contentManager.delete(id);
    }
}
