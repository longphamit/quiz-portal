package com.longpc.devmon.portal.quizportal.service.impl;

import com.longpc.devmon.portal.quizportal.constant.StatusEnum;
import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.quiz.Content;
import com.longpc.devmon.portal.quizportal.entity.quiz.QR;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuestionAnswerTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionAnswerSubmit;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuizSubmit;
import com.longpc.devmon.portal.quizportal.exception.QuizSubmittedException;
import com.longpc.devmon.portal.quizportal.manager.ContentManager;
import com.longpc.devmon.portal.quizportal.manager.QuizSubmitManager;
import com.longpc.devmon.portal.quizportal.service.QuizSubmitService;
import com.longpc.devmon.portal.quizportal.util.DataUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Long PC
 * 28/03/2024| 15:46 | 2024
 **/
@Getter
@Setter
@Service
public class QuizSubmitServiceImpl implements QuizSubmitService {
    @Autowired
    private QuizSubmitManager quizSubmitManager;

    @Autowired
    private ContentManager contentManager;

    @SneakyThrows
    public List<QuizSubmit> generateSurvey(String quizId, List<QuestionTemplate> questionTemplates, String url) {
        List<QuizSubmit> quizSubmits = new ArrayList<>();
        for (int i = 0; i < questionTemplates.size(); i++) {
            QuizSubmit quizSubmit = new QuizSubmit();
            quizSubmit.setId(DataUtil.generateId());
            quizSubmit.setQuizId(quizId);
            QuestionAnswerSubmit questionAnswerSubmit = new QuestionAnswerSubmit();
            questionAnswerSubmit.setQuestionTemplateId(questionTemplates.get(i).getId());
            List<QuestionAnswerSubmit> questionAnswerSubmits = new ArrayList<>();
            questionAnswerSubmits.add(questionAnswerSubmit);
            quizSubmit.setQuestionAnswerSubmits(questionAnswerSubmits);
            ///
            QR qr = new QR();
            qr.setUrl(url + "/" + quizSubmit.getId());
            qr.setImage(DataUtil.generateQRCodeImage(url + "/" + quizSubmit.getId()));
            quizSubmit.setQr(qr);
            //
            quizSubmits.add(quizSubmit);
        }
        List<Content> contents = contentManager.getByReferTypeAndReferId("QUIZ", quizId);
        if (!ObjectUtils.isEmpty(contents) && !ObjectUtils.isEmpty(quizSubmits)) {
            for (Content content : contents) {
                for (int i = 0; i < quizSubmits.size(); i++) {
                    if (i >= content.getStartGuideIndex() - 1 && i <= content.getEndGuideIndex() - 1) {
                        quizSubmits.get(i).setGuideId(content.getId());
                    }
                }
            }
        }

        return quizSubmits;
    }

    @SneakyThrows
    public void syncURLQR(String quizId, String url) {
        List<QuizSubmit> quizSubmits = quizSubmitManager.getByQuizId(quizId);
        for (QuizSubmit quizSubmit : quizSubmits) {
            QR qr = new QR();
            qr.setUrl(url + "/" + quizSubmit.getId());
            qr.setImage(DataUtil.generateQRCodeImage(url + "/" + quizSubmit.getId()));
            quizSubmit.setQr(qr);
            quizSubmitManager.updateAttribute(quizSubmit.getId(), "qr", qr, quizSubmit.getCreatedBy());
        }
    }


    @Override
    public QuizSubmit getById(String id) {
        return quizSubmitManager.getById(id);
    }

    public void add(List<QuizSubmit> quizSubmits, String performerId) {
        for (QuizSubmit quizSubmit : quizSubmits) {
            quizSubmit.setStatus(StatusEnum.QuizStatus.NEW.getCode());
            quizSubmitManager.save(quizSubmit, performerId);
        }
    }

    public void submit(QuizSubmit quizSubmit) {
        quizSubmit.setStatus(StatusEnum.QuizStatus.DONE.getCode());
        quizSubmit.setSubmitTime(new Date());
        quizSubmitManager.save(quizSubmit, quizSubmit.getSubmitPartyId());
    }

    public QuizSubmit getByIdToSubmit(String id) throws QuizSubmittedException {
        QuizSubmit quizSubmit = quizSubmitManager.getById(id);
        if (quizSubmit.getStatus() == StatusEnum.QuizStatus.DONE.getCode()) {
            throw new QuizSubmittedException();
        }
        return quizSubmit;
    }

    @Override
    public List<QuizSubmit> getByQuizId(String quizId) {
        return quizSubmitManager.getByQuizId(quizId);
    }

    public long countByQuizIdAndSubjectCodes(String quizId, List<String> subjectCodes) {
        return quizSubmitManager.countByQuizIdAndSubjectCodes(quizId, subjectCodes);
    }

    public void remove(List<String> ids) {
        quizSubmitManager.remove(ids);
    }
}
