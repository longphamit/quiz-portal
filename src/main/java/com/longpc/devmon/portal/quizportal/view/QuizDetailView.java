package com.longpc.devmon.portal.quizportal.view;

import com.longpc.devmon.portal.quizportal.config.scope.view.ViewScope;
import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.Field;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuestionAnswerTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.Quiz;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuizSubmit;
import com.longpc.devmon.portal.quizportal.service.*;
import com.longpc.devmon.portal.quizportal.util.DataUtil;
import com.longpc.devmon.portal.quizportal.util.SessionUtil;
import com.longpc.devmon.portal.quizportal.view.model.AutoAddQuestionViewModel;
import com.longpc.devmon.portal.quizportal.view.model.CountResultSubmitSubjectModel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Long PC
 * 04/03/2024| 22:46 | 2024
 **/
@Component("QuizDetailView")
@ViewScope
@Getter
@Setter
public class QuizDetailView extends BaseView {
    private String mode;
    private List<Field> fields;
    private QuizSubject quizSubjectAddObject;
    @Autowired
    private QuestionTemplateService questionTemplateService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private QuizSubjectService quizSubjectService;
    @Autowired
    private FieldService fieldService;
    @Autowired
    private QuizSubmitService quizSubmitService;
    private String quizId;
    private Quiz quiz;
    private AutoAddQuestionViewModel autoAddQuestionViewModel;
    private List<QuizSubject> quizSubjects;
    private List<QuizSubmit> quizSubmits;
    @Value("${fe.host.survey}")
    private String surveyHost;


    public void onLoad() {
        if (checkPostBack()) {
            mode = getRequestParam("mode");
            fields = fieldService.getAllField();
            quizId = getRequestParam("id");
            quiz = quizService.getById(quizId);
            autoAddQuestionViewModel = new AutoAddQuestionViewModel();
            if (ObjectUtils.isEmpty(quiz) && mode.equals(DetailPageMode.CREATE.name())) {
                quiz = quizService.generateById(quizId, SessionUtil.getLoginId());
                updateComponents("frmData");
            }
            quizSubjectAddObject = new QuizSubject();
            if (quiz.getType().equals(TypeEnum.Quiz.SURVEY.name())) {
                quizSubjects = quizSubjectService.getByIds(quiz.getQuizSubjectIds());
            }
            quizSubmits = quizSubmitService.getByQuizId(quizId);

        }
    }

    public void randomAddQuestion() {
        if (!(autoAddQuestionViewModel.getNumEasy() == 0 && autoAddQuestionViewModel.getNumMedium() == 0 && autoAddQuestionViewModel.getNumHard() == 0)) {
            List<QuestionTemplate> questionTemplates = questionTemplateService.getRandomForAddToQuiz(autoAddQuestionViewModel.getNumEasy(), autoAddQuestionViewModel.getNumMedium(), autoAddQuestionViewModel.getNumHard());
            quiz.setQuestionTemplates(questionTemplates);
            quizService.updateQuestionTemplates(quizId, quiz.getQuestionTemplates(), SessionUtil.getLoginId());
        } else {
            addError("Vui lòng nhập giá trị");
        }

    }

    public void updateName() {
        quizService.updateName(quizId, quiz.getName(), SessionUtil.getLoginId());
    }

    public void removeQuiz() {
        quizService.remove(quizId, SessionUtil.getLoginId());
        redirectPageInCurrentTab("/pages/quiz/index.xhtml");
    }

    public void removeQuestion(String questionId) {
        quizService.removeQuestion(quizId, questionId, SessionUtil.getLoginId());
        quiz = quizService.getById(quizId);
    }

    public void updateFieldId() {
        quizService.updateFieldId(quizId, quiz.getFieldId(), SessionUtil.getLoginId());
    }

    public void updateProcessType() {
        quizService.updateProcessType(quizId, quiz.getProcessType(), SessionUtil.getLoginId());
    }

    public void updateType() {
        quizService.updateType(quizId, TypeEnum.Quiz.valueOf(quiz.getType()), SessionUtil.getLoginId());
        if (quiz.getType().equals(TypeEnum.Quiz.SURVEY.name())) {
            quizSubjects = quizSubjectService.getByIds(quiz.getQuizSubjectIds());
        }
    }

    public void updateGuide() {
        quizService.updateGuide(quizId, quiz.getGuide(), SessionUtil.getLoginId());
        addMessage("Cập nhật thành công");
    }

    public void addQuizSubject() {
        QuizSubject quizSubject = quizSubjectService.create(quizSubjectAddObject.getName(), quizSubjectAddObject.getKey(), SessionUtil.getLoginId());
        quizService.addQuizSubjectId(quizId, quizSubject.getId(), SessionUtil.getLoginId());
        quiz = quizService.getById(quizId);
        quizSubjects = quizSubjectService.getByIds(quiz.getQuizSubjectIds());
        quizSubjectAddObject = new QuizSubject();
        hideDialog("dlgCreateQuizSubject");
    }

    public void generateQuizSubjectCodes(String quizSubjectId) {
        quizSubjectService.generateCode(quizSubjectId, quizId, SessionUtil.getLoginId());
        quizSubjects = quizSubjectService.getByIds(quiz.getQuizSubjectIds());
    }

    @SneakyThrows
    public void addQuestionCompareQuizSubject() {
        if (ObjectUtils.isEmpty(quizSubjects)) {
            addError("Chưa có mẫu vật");
            return;
        }
        quizService.removeAllQuestionTemplate(quizId);
        Map<String, String> codes = new HashMap();
        Map<String, String> names = new HashMap();
        quizService.generateQuestionForPairSurvey(quizId, "");
        quiz = quizService.getById(quizId);
        if (!ObjectUtils.isEmpty(quizSubmits)) {
            quizSubmitService.remove(quizSubmits.stream().map(QuizSubmit::getId).collect(Collectors.toList()));
            quizSubmits = quizSubmitService.getByQuizId(quizId);
        }
    }


    public void addQuizSubmit() {
        quizSubmits = quizSubmitService.generateSurveyTypePair(quizId, quiz.getQuestionTemplates(), surveyHost + "/survey/submit");
        quizSubmitService.add(quizSubmits, SessionUtil.getLoginId());
    }

    public void updateParticipants() {
        if (quiz.getParticipantsLimit() % 2 != 0) {
            Quiz oldQuiz = quizService.getById(quizId);
            quiz.setParticipantsLimit(oldQuiz.getParticipantsLimit());
            addError("Số lượng người tham gia phải là số chẵn");
            return;
        }
        quizService.updateParticipantsLimit(quizId, quiz.getParticipantsLimit(), SessionUtil.getLoginId());
    }
}
