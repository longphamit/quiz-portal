package com.longpc.devmon.portal.quizportal.service.impl;

import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuestionAnswerTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import com.longpc.devmon.portal.quizportal.manager.QuestionTemplateManager;
import com.longpc.devmon.portal.quizportal.service.QuestionTemplateService;
import com.longpc.devmon.portal.quizportal.util.BaseSort;
import com.longpc.devmon.portal.quizportal.util.DataUtil;
import com.longpc.devmon.portal.quizportal.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Long PC
 * 04/03/2024| 22:26 | 2024
 **/
@Component
public class QuestionTemplateServiceImpl implements QuestionTemplateService {

    private final int PERMUTAION_TRIANGLE = 3;
    private final int PERMUTAION_PAIR = 2;


    @Autowired
    public QuestionTemplateManager questionTemplateManager;

    @Override
    public QuestionTemplate getById(String id) {
        return questionTemplateManager.getById(id);
    }

    public QuestionTemplate generateById(String id, TypeEnum.Question type, String performerId) {
        QuestionTemplate questionTemplate = new QuestionTemplate();
        questionTemplate.setId(id);
        List<QuestionAnswerTemplate> answerTemplates = new ArrayList<>();
        if (type.equals(TypeEnum.Question.SELECT_ONE)) {
            for (int i = 1; i < 5; i++) {
                QuestionAnswerTemplate questionAnswerTemplate = new QuestionAnswerTemplate();
                questionAnswerTemplate.setId(DataUtil.generateId());
                questionAnswerTemplate.setKey(Integer.toString(i));
                questionAnswerTemplate.setType(type.name());
                questionTemplate.setCreatedTime(new Date());
                questionTemplate.setCreatedBy(performerId);
                questionAnswerTemplate.setKeyLabel(TypeEnum.lookUp_QuestionAnswerKey(i).name());
                answerTemplates.add(questionAnswerTemplate);
            }
            questionTemplate.setQuestionAnswerTemplates(answerTemplates);
        }
        return (QuestionTemplate) questionTemplateManager.save(questionTemplate, performerId);
    }

    private List<QuestionTemplate> generateQuestion(String quizId,
                                                    TypeEnum.Question questionType,
                                                    List<QuizSubject> quizSubjects,
                                                    int permutationNumber,
                                                    int participantsLimit,
                                                    String performerId) {

        StringBuilder keys = new StringBuilder();
        List<String> subjectNames = new ArrayList<>();
        Map<String, QuizSubject> quizSubjectMap = new HashMap<>();
        for (QuizSubject quizSubject : quizSubjects) {
            subjectNames.add(quizSubject.getKey());
            quizSubjectMap.put(quizSubject.getKey(), quizSubject);
        }

        for (String s : subjectNames) {
            keys.append(s);
        }
        List<String> keyPermutations = generatePermutations(keys.toString(), permutationNumber);
        keyPermutations.remove(keyPermutations.size() - 1);
        keyPermutations.remove(0);

        Map<String, List<String>> subjectAndCodes = new HashMap<>();
        for (QuizSubject subject : quizSubjects) {
            ArrayList<String> codes = new ArrayList<>(subject.getCodes());
            Collections.shuffle(codes);
            subjectAndCodes.put(subject.getKey(), codes);
        }

        List<QuestionTemplate> rs = new ArrayList<>();
        // so nguoi tham gia/ so luong to hop

        for (int i = 0; i < participantsLimit / keyPermutations.size(); i++) {
            List<QuestionTemplate> questionTemplates = new ArrayList<>();
            for (String s : keyPermutations) {
                String[] keysArr = s.split("");
                QuestionTemplate questionTemplate = new QuestionTemplate();
                questionTemplate.setContent(s);
                List<QuestionAnswerTemplate> questionAnswerTemplates = new ArrayList<>();

                for (String key : keysArr) {
                    List<String> codes = subjectAndCodes.get(key);
                    String code = codes.get(0);
                    codes.remove(0);
                    subjectAndCodes.put(key, codes);

                    QuestionAnswerTemplate questionAnswerTemplate = new QuestionAnswerTemplate();
                    questionAnswerTemplate.setKey(code);
                    questionAnswerTemplate.setKeyLabel(code);
                    questionAnswerTemplate.setId(DataUtil.generateId());
                    questionAnswerTemplate.setType(questionType.name());
                    questionAnswerTemplate.setCreatedTime(new Date());
                    questionAnswerTemplates.add(questionAnswerTemplate);
                }
                questionTemplate.setQuestionAnswerTemplates(questionAnswerTemplates);
                questionTemplate.setId(DataUtil.generateId());
                questionTemplate.setCreatedTime(new Date());
                questionTemplate.setCreatedBy(SessionUtil.getLoginId());
                questionTemplates.add(questionTemplate);
            }
            rs.addAll(questionTemplates);
        }
        return rs;
    }


    private ArrayList<String> generatePermutations(String str, int length) {
        ArrayList<String> permutations = new ArrayList<>();
        generatePermutationsHelper("", str, length, permutations);
        // kiểm tra xem

        return permutations;
    }

    private void generatePermutationsHelper(String prefix, String str, int length, ArrayList<String> permutations) {
        if (length == 0) {
            permutations.add(prefix);
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            generatePermutationsHelper(prefix + str.charAt(i), str, length - 1, permutations);
        }
    }

    public List<QuestionTemplate> generatePairById(
            String quizId,
            List<QuizSubject> quizSubjects,
            int participantsNumber,
            String performerId) {
        return generateQuestion(quizId, TypeEnum.Question.RADIO, quizSubjects, PERMUTAION_PAIR, participantsNumber, performerId);
    }

    public List<QuestionTemplate> generateTriangleById(
            String quizId,
            List<QuizSubject> quizSubjects,
            int participantsNumber,
            String performerId) {
        return generateQuestion(quizId, TypeEnum.Question.RADIO, quizSubjects, PERMUTAION_TRIANGLE, participantsNumber, performerId);
    }


    @Override
    public void updateContent(String id, String content, String performerId) {
        questionTemplateManager.updateContent(id, content, performerId);
        String encodeContent = DataUtil.generateSlug(content);
        questionTemplateManager.updateEncodeContent(id, encodeContent, performerId);
    }

    @Override
    public void updateAnswerContent(String id, String answerId, String content, String performerId) {
        questionTemplateManager.updateAnswerTemplateContent(id, answerId, content, performerId);
    }


    public Page<QuestionTemplate> search(int pageNumber, int pageSize, List<BaseSort> sorts, String performerId) {
        return questionTemplateManager.search(pageNumber, pageSize, sorts, "");
    }

    public void remove(String id, String performerId) {
        questionTemplateManager.remove(id, performerId);
    }

    public void updateLevel(String id, int level, String performerId) {
        questionTemplateManager.updateLevel(id, level, performerId);
    }

    public void updateQuestionAnswerTemplateResultId(String id, String questionTemplateAnswerId, String performerId) {
        questionTemplateManager.updateQuestionAnswerTemplateResultId(id, questionTemplateAnswerId, performerId);
    }

    public List<QuestionTemplate> getRandomForAddToQuiz(int numEasy, int numMedium, int numHard) {
        return questionTemplateManager.getRandomForAddToQuiz(numEasy, numMedium, numHard);
    }
}
