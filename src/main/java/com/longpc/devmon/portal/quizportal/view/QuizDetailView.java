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
        int countCodes = 0;
        if (quiz.getParticipantsLimit() % 2 != 0) {
            addError("Số lượng người tham gia phải là số chẵn");
            return;
        }
        if (quiz.getProcessType().equals(TypeEnum.QuizProcessType.PAIR.name())) {
            countCodes = quiz.getParticipantsLimit();
        } else if (quiz.getProcessType().equals(TypeEnum.QuizProcessType.TRIANGLE.name())) {
            countCodes = quiz.getParticipantsLimit() + quiz.getParticipantsLimit() / 2;
        }
        List<String> existCode = new ArrayList<>();
        for (QuizSubject quizSubject : quizSubjects) {
            if (!ObjectUtils.isEmpty(quizSubject.getCodes()) && !quizSubject.getId().equals(quizSubjectId)) {
                existCode.addAll(quizSubject.getCodes());
            }
        }
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < countCodes; i++) {
            List<Integer> digits = new ArrayList<>();
            for (int j = 0; j <= 9; j++) {
                digits.add(j);
            }

            boolean checkFails = false;
            String code = new String();
            do {
                Collections.shuffle(digits);
                List<Integer> uniqueDigits = digits.subList(0, 3);
                StringBuilder rs = new StringBuilder();
                for (Integer integer : uniqueDigits) {
                    rs.append(integer);
                }
                code = rs.toString();


                if (code.startsWith("0")) {
                    checkFails = true;
                    System.out.println(" ==== CHECK CODE START 0 ==== " + code);
                } else if (existCode.contains(code) || codes.contains(code)) {
                    System.out.println(" ==== CHECK CODE EXIST ==== " + code);
                    checkFails = true;
                } else if ((uniqueDigits.get(1) == uniqueDigits.get(0) + 1) && (uniqueDigits.get(2) == uniqueDigits.get(1) + 1)) {
                    System.out.println(" ==== CHECK CODE 123 ==== " + code);
                    checkFails = true;
                } else if ((uniqueDigits.get(0) + uniqueDigits.get(1) + uniqueDigits.get(2)) % 3 != 0) {
                    checkFails = true;
                    System.out.println(" ==== CHECK % 3 != 0 ==== " + code);
                } else {
                    checkFails = false;
                }
            } while (checkFails);
            if (!ObjectUtils.isEmpty(code)) {
                codes.add(code);
            }
        }
        quizSubjectService.updateCodes(quizSubjectId, codes, SessionUtil.getLoginId());
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
        generateQuestionCompareQuizSubject(false, codes, names);
        generateQuestionCompareQuizSubject(true, codes, names);
        quiz = quizService.getById(quizId);
        if (!ObjectUtils.isEmpty(quizSubmits)) {
            quizSubmitService.remove(quizSubmits.stream().map(QuizSubmit::getId).collect(Collectors.toList()));
            quizSubmits = quizSubmitService.getByQuizId(quizId);
        }
    }

    private void generateQuestionCompareQuizSubject(boolean isReverse, Map<String, String> codes, Map<String, String> names) {
        for (int i = 0; i < quiz.getParticipantsLimit() / 2; i++) {
            List<QuestionTemplate> questionTemplates = null;
            boolean checkExist;
            do {
                checkExist = false;
                questionTemplates = questionTemplateService.generateById(
                        DataUtil.generateId(),
                        isReverse,
                        TypeEnum.Question.RADIO,
                        TypeEnum.QuizProcessType.valueOf(quiz.getProcessType()),
                        codes,
                        names,
                        quizSubjects, SessionUtil.getLoginId());

                checkExist = quizService.checkExistQuestionTemplate(quizId, questionTemplates);

            } while (checkExist);
            if (!ObjectUtils.isEmpty(questionTemplates)) {
                System.out.println("======= ADD QUESTION TEMPLATE " + i + " ======");
                quizService.addQuestionTemplates(quizId, questionTemplates, SessionUtil.getLoginId());
                for (QuestionTemplate questionTemplate : questionTemplates) {
                    for (QuestionAnswerTemplate key : questionTemplate.getQuestionAnswerTemplates()) {
                        codes.put(key.getKey(), key.getKey());
                    }
                }
            }
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
