package com.longpc.devmon.portal.quizportal.gateway;

import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.service.QuestionTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Long PC
 * 25/03/2024| 14:58 | 2024
 **/
@RestController
@RequestMapping("gateway/question-template")
@CrossOrigin
public class QuestionTemplateGateway {
    @Autowired
    QuestionTemplateService questionTemplateService;

    @GetMapping("{id}")
    public QuestionTemplate get(@PathVariable("id") String id) {
        return questionTemplateService.getById(id);
    }
}
