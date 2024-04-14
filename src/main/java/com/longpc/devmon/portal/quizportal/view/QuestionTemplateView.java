package com.longpc.devmon.portal.quizportal.view;

import com.longpc.devmon.portal.quizportal.config.scope.view.ViewScope;
import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.service.QuestionTemplateService;
import com.longpc.devmon.portal.quizportal.util.DataUtil;
import com.longpc.devmon.portal.quizportal.view.model.QuestionTemplateLazyViewModel;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Long PC
 * 04/03/2024| 22:28 | 2024
 **/
@ViewScope
@Getter
@Setter
@Component("QuestionTemplateView")
public class QuestionTemplateView extends BaseView {
    @Autowired
    private QuestionTemplateLazyViewModel questionTemplateLazyViewModel;

    @Autowired
    private QuestionTemplateService questionTemplateService;
    private UploadedFile excelImported;

    public void onLoad() {
        if (checkPostBack()) {

        }
    }

    public void addQuestion() {
        String id = DataUtil.generateId();
        redirectPageInNewTab("/pages/question-template/detail.xhtml?id=" + id + "&mode=" + DetailPageMode.CREATE.name());
    }

    public void viewDetail(String id) {
        redirectPageInNewTab("/pages/question-template/detail.xhtml?id=" + id + "&mode=" + DetailPageMode.VIEW.name());
    }

    public void handleFileUpload(FileUploadEvent fileUploadEvent) {
        System.out.println("");
    }

    public void remove(String id){
        questionTemplateService.remove(id, "");
        addMessage("Xóa thành công");
    }
}
