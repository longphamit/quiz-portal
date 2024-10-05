package com.longpc.devmon.portal.quizportal.service.impl;

import com.longpc.devmon.portal.quizportal.constant.TypeEnum;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuestionAnswerTemplate;
import com.longpc.devmon.portal.quizportal.entity.quiz.QuizSubject;
import com.longpc.devmon.portal.quizportal.entity.quiz.submit.QuestionTemplate;
import com.longpc.devmon.portal.quizportal.service.SurveyService;
import com.longpc.devmon.portal.quizportal.util.DataUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Long PC
 * 27/03/2024| 22:31 | 2024
 **/
@Service
public class SurveyServiceImpl implements SurveyService {
    public List<QuestionTemplate> generatePairById(String id,
                                                   boolean isReverse,
                                                   TypeEnum.Question type,
                                                   TypeEnum.QuizProcessType processType,
                                                   Map<String, String> codes,
                                                   Map<String, String> names,
                                                   List<QuizSubject> quizSubjects,
                                                   String performerId) {
        List<QuizSubject> quizSubjectClones = new ArrayList<>();
        for (QuizSubject quizSubject : quizSubjects) {
            QuizSubject quizSubjectClone = new QuizSubject();
            quizSubjectClone.setKey(quizSubject.getKey());
            quizSubjectClone.setCodes(quizSubject.getCodes());
            quizSubjectClone.setId(quizSubject.getId());
            quizSubjectClones.add(quizSubjectClone);
        }
        if (isReverse) {
            Collections.reverse(quizSubjectClones);
        }

        int countNames = 1;
        String foundName = new String();
        // tạo câu hỏi theo tổ hợp AB, AAB, ...
        for (int i = 0; i < countNames; i++) {
            StringBuilder name = new StringBuilder();
            List<String> subjectNames = quizSubjectClones.stream().map(e -> e.getKey()).collect(Collectors.toList());
            if (processType.equals(TypeEnum.QuizProcessType.PAIR)) {
                for (String subjectName : subjectNames) {
                    name.append(" [" + subjectName + "] ");
                }
                foundName = name.toString();
                names.put(name.toString(), name.toString());
            }
        }
        List<QuestionTemplate> rs = new ArrayList<>();
        QuestionTemplate questionTemplate = new QuestionTemplate();
        questionTemplate.setId(id);
        questionTemplate.setContent(foundName);
        List<QuestionAnswerTemplate> answerTemplates = new ArrayList<>();
        // tạo câu tra lời cho câu hỏi
        if (processType.equals(TypeEnum.QuizProcessType.PAIR)) {
            for (int i = 0; i < 2; i++) {
                Collections.shuffle(quizSubjectClones.get(i).getCodes());
                QuestionAnswerTemplate questionAnswerTemplate = new QuestionAnswerTemplate();
                questionAnswerTemplate.setId(DataUtil.generateId());
                questionAnswerTemplate.setType(type.name());
                questionTemplate.setCreatedTime(new Date());
                questionTemplate.setCreatedBy(performerId);

                questionAnswerTemplate.setKey(quizSubjectClones.get(i).getCodes().get(0));
                questionAnswerTemplate.setKeyLabel(quizSubjectClones.get(i).getCodes().get(0));

                questionAnswerTemplate.setQuizSubjectId(quizSubjectClones.get(i).getId());
                answerTemplates.add(questionAnswerTemplate);
            }
            questionTemplate.setQuestionAnswerTemplates(answerTemplates);
        }

        rs.add(questionTemplate);
        return rs;
    }

    @Override
    public List<QuestionTemplate> generateTriangleById(String id, boolean isReverse,
                                                       TypeEnum.Question type,
                                                       TypeEnum.QuizProcessType processType,
                                                       Map<String, String> codes,
                                                       Map<String, String> names,
                                                       List<QuizSubject> quizSubjects,
                                                       String performerId
    ) {
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
        List<String> keyPermutations = generatePermutations(keys.toString(), 3);
        keyPermutations.remove(0);
        keyPermutations.remove(keyPermutations.size() - 1);
        List<QuestionTemplate> rs = new ArrayList<>();
        for (String s : keyPermutations) {
            QuestionTemplate questionTemplate = new QuestionTemplate();
            questionTemplate.setId(id);
            questionTemplate.setContent(s);
            List<QuestionAnswerTemplate> answerTemplates = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                Collections.shuffle(quizSubjectMap.get(String.valueOf(s.charAt(i))).getCodes());
                QuestionAnswerTemplate questionAnswerTemplate = new QuestionAnswerTemplate();
                questionAnswerTemplate.setId(DataUtil.generateId());
                questionAnswerTemplate.setType(type.name());
                questionTemplate.setCreatedTime(new Date());
                questionTemplate.setCreatedBy(performerId);

                questionAnswerTemplate.setKey(quizSubjectMap.get(String.valueOf(s.charAt(i))).getCodes().get(0));
                questionAnswerTemplate.setKeyLabel(quizSubjectMap.get(String.valueOf(s.charAt(i))).getCodes().get(0));

                questionAnswerTemplate.setQuizSubjectId(quizSubjectMap.get(String.valueOf(s.charAt(i))).getId());
                answerTemplates.add(questionAnswerTemplate);
            }
            questionTemplate.setQuestionAnswerTemplates(answerTemplates);
            rs.add(questionTemplate);
        }


        return new ArrayList<>(rs);
    }

    private ArrayList<String> generatePermutations(String str, int length) {
        ArrayList<String> permutations = new ArrayList<>();
        generatePermutationsHelper("", str, length, permutations);
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
}
