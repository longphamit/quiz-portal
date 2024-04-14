package com.longpc.devmon.portal.quizportal.view;

import com.longpc.devmon.portal.quizportal.config.scope.view.ViewScope;
import com.longpc.devmon.portal.quizportal.entity.Party;
import com.longpc.devmon.portal.quizportal.entity.quiz.Quiz;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuizSubmit;
import com.longpc.devmon.portal.quizportal.service.PartyService;
import com.longpc.devmon.portal.quizportal.service.QuizService;
import com.longpc.devmon.portal.quizportal.service.QuizSubjectService;
import com.longpc.devmon.portal.quizportal.service.QuizSubmitService;
import com.longpc.devmon.portal.quizportal.view.model.CountResultSubmitSubjectModel;
import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Long PC
 * 03/04/2024| 19:55 | 2024
 **/
@Component("SurveyResultView")
@ViewScope
@Getter
@Setter
public class SurveyResultView extends BaseView {
    private Map<String, CountResultSubmitSubjectModel> countResultSubmitSubjectModelMap;
    private List<QuizSubject> quizSubjects;
    private String quizId;
    private Quiz quiz;
    private PieChartModel subjectVoteChartModel;
    @Autowired
    private QuizService quizService;
    @Autowired
    private QuizSubmitService quizSubmitService;
    @Autowired
    private QuizSubjectService quizSubjectService;
    @Autowired
    private PartyService partyService;
    private List<QuizSubmit> quizSubmits;
    private Map<String, Party> partyMap;
    private Map<String, QuestionTemplate> questionTemplateMap;
    private Map<String, QuizSubject> quizSubjectCodeMap;

    public SurveyResultView() {
        countResultSubmitSubjectModelMap = new HashMap<>();
        questionTemplateMap = new HashMap<>();
        partyMap = new HashMap<>();
        quizSubjectCodeMap= new HashMap<>();
    }

    public void onLoad() {
        if (checkPostBack()) {
            quizId = getRequestParam("id");
            quiz = quizService.getById(quizId);
            quizSubjects = quizSubjectService.getByIds(quiz.getQuizSubjectIds());
            for (QuizSubject quizSubject : quizSubjects) {
                for (String code : quizSubject.getCodes()) {
                    quizSubjectCodeMap.put(code, quizSubject);
                }
            }

            quizSubmits = quizSubmitService.getByQuizId(quizId);
            createSubjectVoteChartModel();
            for (QuestionTemplate questionTemplate : quiz.getQuestionTemplates()) {
                questionTemplateMap.put(questionTemplate.getId(), questionTemplate);
            }
            List<String> partyIds = new ArrayList<>();
            for (QuizSubmit quizSubmit : quizSubmits) {
                if (!ObjectUtils.isEmpty(quizSubmit.getSubmitPartyId())) {
                    partyIds.add(quizSubmit.getSubmitPartyId());
                }
            }
            List<Party> parties = partyService.getByIds(partyIds);
            for (Party party : parties) {
                partyMap.put(party.getId(), party);
            }
        }
    }

    private void createSubjectVoteChartModel() {
        if (!ObjectUtils.isEmpty(quizSubjects)) {
            for (QuizSubject quizSubject : quizSubjects) {
                long count = quizSubmitService.countByQuizIdAndSubjectCodes(quizId, quizSubject.getCodes());
                CountResultSubmitSubjectModel countResultSubmitSubjectModel = CountResultSubmitSubjectModel.builder().quizSubject(quizSubject).count(count).build();
                countResultSubmitSubjectModelMap.put(quizSubject.getId(), countResultSubmitSubjectModel);
            }
        }


        subjectVoteChartModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        for (CountResultSubmitSubjectModel countResultSubmitSubjectModel : countResultSubmitSubjectModelMap.values()) {
            values.add(countResultSubmitSubjectModel.getCount());
        }
        dataSet.setData(values);
        //
        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        dataSet.setBackgroundColor(bgColors);
        //
        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        for (CountResultSubmitSubjectModel countResultSubmitSubjectModel : countResultSubmitSubjectModelMap.values()) {
            labels.add(countResultSubmitSubjectModel.getQuizSubject().getName());

        }
        data.setLabels(labels);
        subjectVoteChartModel.setData(data);

    }

    public List<CountResultSubmitSubjectModel> getCountResultSubmitSubjectModelList() {
        return new ArrayList<>(countResultSubmitSubjectModelMap.values());
    }
    public void viewSurveyResult(String id){
        redirectPageInNewTab("https://survey.longpc.site/survey/result/"+id);
    }
}
