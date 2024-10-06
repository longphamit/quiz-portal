package com.longpc.devmon.portal.quizportal.gateway;

import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.Party;
import com.longpc.devmon.portal.quizportal.entity.quiz.Quiz;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuizSubmit;
import com.longpc.devmon.portal.quizportal.gateway.model.CountResultSubjectCodesPairModel;
import com.longpc.devmon.portal.quizportal.gateway.model.CreateQuizSubjectGWModel;
import com.longpc.devmon.portal.quizportal.gateway.model.UpdateQuizBaseData;
import com.longpc.devmon.portal.quizportal.service.PartyService;
import com.longpc.devmon.portal.quizportal.service.QuizService;
import com.longpc.devmon.portal.quizportal.service.QuizSubjectService;
import com.longpc.devmon.portal.quizportal.service.QuizSubmitService;
import com.longpc.devmon.portal.quizportal.util.DataUtil;
import com.longpc.devmon.portal.quizportal.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Long PC
 * 25/03/2024| 14:54 | 2024
 **/
@RestController
@RequestMapping("gateway/quiz")
@CrossOrigin("*")
public class QuizGateway {
    @Autowired
    QuizService quizService;
    @Autowired
    QuizSubjectService quizSubjectService;
    @Autowired
    QuizSubmitService quizSubmitService;
    @Autowired
    PartyService partyService;
    @Value("${fe.host.survey}")
    private String surveyHost;

    @GetMapping("{id}")
    public Quiz get(@PathVariable("id") String id) {
        return quizService.getById(id);
    }

    @GetMapping("{id}/question-template/{questionId}")
    public QuestionTemplate getQuestionTemplate(
            @PathVariable("id") String id,
            @PathVariable("questionId") String questionId) {
        return quizService.getQuestionTemplate(id, questionId);
    }

    @GetMapping("survey/generate/process-type/{processType}")
    public Quiz generateQuiz(
            @RequestHeader("PartyId") String partyId,
            @PathVariable("processType") String processType) {
        String id = DataUtil.generateId();
        return quizService.generateSurveyById(id, processType, partyId);
    }

    @GetMapping("{id}/quiz-subject")
    public List<QuizSubject> getQuizSubjectByQuizId(@PathVariable("id") String quizId) {
        return quizSubjectService.getByIds(quizService.getById(quizId).getQuizSubjectIds());
    }

    @PostMapping("{id}/quiz-subject")
    public QuizSubject createQuizSubjectByQuizId(
            @PathVariable("id") String quizId,
            @RequestBody CreateQuizSubjectGWModel createQuizSubjectGWModel) {
        {
            QuizSubject quizSubject = quizSubjectService.create(createQuizSubjectGWModel.getName(), createQuizSubjectGWModel.getKey(), "");
            quizSubjectService.generateCode(quizSubject.getId(), quizId, SessionUtil.getLoginId());
            quizService.addQuizSubjectId(quizId, quizSubject.getId(), SessionUtil.getLoginId());
            return quizSubject;
        }
    }

    @GetMapping("{id}/quiz-subject/{quizSubjectId}/code/generate")
    public QuizSubject createQuizSubjectByQuizId(
            @PathVariable("id") String quizId,
            @PathVariable("quizSubjectId") String quizSubjectId) {
        quizSubjectService.generateCode(quizSubjectId, quizId, SessionUtil.getLoginId());
        return quizSubjectService.getById(quizSubjectId);
    }

    @GetMapping("{id}/question/generate")
    public Quiz generateQuestion(@PathVariable("id") String id) {
        Quiz quiz = quizService.getById(id);
        quizService.generateQuestionForSurvey(id, TypeEnum.QuizProcessType.valueOf(quiz.getProcessType()), SessionUtil.getLoginId());
        return quizService.getById(id);
    }

    @GetMapping("{id}/quiz-submit")
    public List<QuizSubmit> getQuizSubmitByQuizId(@PathVariable("id") String quizId) {
        return quizSubmitService.getByQuizId(quizId);
    }

    @PutMapping("{id}/update-base")
    public Quiz updateQuizBase(
            @PathVariable("id") String id,
            @RequestBody UpdateQuizBaseData updateQuizBaseData) {
        try {
            quizService.updateName(id, updateQuizBaseData.getName(), "");
            quizService.updateGuide(id, updateQuizBaseData.getGuide(), "");
            quizService.updateParticipantsLimit(id, updateQuizBaseData.getParticipantsLimit(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quizService.getById(id);
    }

    @GetMapping
    public List<Quiz> getQuizs() {
        return quizService.getAll();
    }

    @GetMapping("created-by")
    public List<Quiz> getByCreatedPartyId(@RequestHeader("PartyId") String partyId) {
        return quizService.getByCreatedPartyId(partyId);
    }


    @GetMapping("{id}/survey/generate")
    public List<QuizSubmit> generateSurvey(@PathVariable("id") String quizId) {
        Quiz quiz = quizService.getById(quizId);
        List<QuizSubmit> quizSubmits = quizSubmitService.generateSurveyTypePair(quizId, quiz.getQuestionTemplates(), surveyHost + "/survey/submit");
        quizSubmitService.add(quizSubmits, SessionUtil.getLoginId());
        return quizSubmits;
    }

    @GetMapping("{id}/submit-parties")
    public List<Party> getSubmitParties(@PathVariable("id") String quizId) {
        List<Party> partyList = new ArrayList<>();
        List<String> partyIds = new ArrayList<>();
        List<QuizSubmit> quizSubmits = quizSubmitService.getByQuizId(quizId);
        for (QuizSubmit quizSubmit : quizSubmits) {
            if (!ObjectUtils.isEmpty(quizSubmit.getSubmitPartyId())) {
                partyIds.add(quizSubmit.getSubmitPartyId());
            }
        }
        return partyService.getByIds(partyIds);
    }

    @GetMapping("{id}/count-result-subject-pair")
    public List<CountResultSubjectCodesPairModel> countResultByQuizIdAndSubjectCodes(@PathVariable("id") String quizId) {
        Quiz quiz = quizService.getById(quizId);
        List<CountResultSubjectCodesPairModel> countResultSubjectCodesPairModels = new ArrayList<>();
        List<QuizSubject> quizSubjects = quizSubjectService.getByIds(quiz.getQuizSubjectIds());
        for (QuizSubject quizSubject : quizSubjects) {
            long count = quizSubmitService.countByQuizIdAndSubjectCodes(quizId, quizSubject.getCodes());
            CountResultSubjectCodesPairModel countResultSubjectCodesPairModel = new CountResultSubjectCodesPairModel();
            countResultSubjectCodesPairModel.setCount(count);
            countResultSubjectCodesPairModel.setSubjectId(quizSubject.getId());
            countResultSubjectCodesPairModel.setSubjectName(quizSubject.getName());
            countResultSubjectCodesPairModel.setSubjectKey(quizSubject.getKey());
            countResultSubjectCodesPairModels.add(countResultSubjectCodesPairModel);
        }
        return countResultSubjectCodesPairModels;
    }
}
