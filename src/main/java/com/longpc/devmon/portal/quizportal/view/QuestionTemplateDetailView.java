package com.longpc.devmon.portal.quizportal.view;

import com.longpc.devmon.portal.quizportal.config.scope.view.ViewScope;
import com.longpc.devmon.portal.quizportal.constant.MessageEnum;
import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.Field;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.service.FieldService;
import com.longpc.devmon.portal.quizportal.service.QuestionTemplateService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Long PC
 * 04/03/2024| 22:47 | 2024
 **/
@ViewScope
@Getter
@Setter
@Component("QuestionTemplateDetailView")
public class QuestionTemplateDetailView extends BaseView {
    @Autowired
    private QuestionTemplateService questionTemplateService;
    @Autowired
    private FieldService fieldService;
    private QuestionTemplate questionTemplate;
    private String questionTemplateId;
    private String mode;
    private boolean isUpdateMode;
    private List<Field> fields;

    public void onLoad() {
        if (checkPostBack()) {
            questionTemplateId = getRequestParam("id");
            mode = getRequestParam("mode");
            questionTemplate = questionTemplateService.getById(questionTemplateId);
            if (ObjectUtils.isEmpty(questionTemplate) && mode.equals(DetailPageMode.CREATE.name())) {
                questionTemplate = questionTemplateService.generateById(questionTemplateId, TypeEnum.Question.SELECT_ONE, "");
                updateComponents("frmData");
            }
            fields = fieldService.getAllField();
        }
    }

    public void updateContent() {
        questionTemplateService.updateContent(questionTemplateId, questionTemplate.getContent(), "");
        //questionTemplate = questionTemplateService.getById(questionTemplateId);
        //addMessage(MessageEnum.Activity.UPDATE_SUCCESS.getMessage());
    }

    public void updateAnswerContent(String answerId, Integer index) {
        questionTemplateService.updateAnswerContent(questionTemplateId, answerId, questionTemplate.getQuestionAnswerTemplates().get(index).getContent(), "");
        questionTemplate = questionTemplateService.getById(questionTemplateId);
        addMessage(MessageEnum.Activity.UPDATE_SUCCESS.getMessage());

    }

    public void removeQuestion() {
        questionTemplateService.remove(this.questionTemplateId, "");
        redirectPageInCurrentTab("/pages/question-template/index.xhtml");
    }

    public void updateQuestionLevel() {
        questionTemplateService.updateLevel(questionTemplateId, questionTemplate.getLevel(), "");
        addMessage(MessageEnum.Activity.UPDATE_SUCCESS.getMessage());
    }

    public void updateQuestionAnswerTemplateResultId() {
        questionTemplateService.updateQuestionAnswerTemplateResultId(questionTemplateId, questionTemplate.getQuestionAnswerTemplateResultId(), "");
        addMessage(MessageEnum.Activity.UPDATE_SUCCESS.getMessage());
    }
}
