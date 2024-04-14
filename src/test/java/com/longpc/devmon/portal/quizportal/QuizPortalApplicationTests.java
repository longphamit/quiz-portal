package com.longpc.devmon.portal.quizportal;

import com.longpc.devmon.portal.quizportal.manager.QuizManager;
import com.longpc.devmon.portal.quizportal.manager.QuizSubmitManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@ExtendWith(MockitoExtension.class)
class QuizPortalApplicationTests {
    @InjectMocks
    MongoTemplate mongoTemplate;
    @InjectMocks
    private QuizSubmitManager quizSubmitManager;

    @Test
    void test() {
        quizSubmitManager.setMongoTemplate(mongoTemplate);
        quizSubmitManager.getByQuizId("GDu2Gc");
    }

}
