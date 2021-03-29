package ru.otus.vbaymurzin.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.vbaymurzin.dao.TestQuestionDao;
import ru.otus.vbaymurzin.domain.TestQuestion;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestQuestionServiceImplTest {

    private static final String TEST_QUESTION = "Test question";
    private static final int CORRECT_ANSWER_INDEX = 1;
    private static final String FIRST_ANSWER = "first";
    private static final String SECOND_ANSWER = "second";
    private static final String THIRD_ANSWER = "third";
    private TestQuestionService testQuestionService;

    @Mock
    private TestQuestionDao dao;

    @BeforeEach
    void setUp() {
        testQuestionService = new TestQuestionServiceImpl(dao);
    }

    @Test
    void getTestQuestionsEmpty() {
        List<TestQuestion> testQuestions = testQuestionService.getTestQuestions();
        assertNotNull(testQuestions);
        assertEquals(testQuestions.size(), 0);
    }

    @Test
    void getTestQuestions() {
        TestQuestion testQuestion = TestQuestion.builder()
                .question(TEST_QUESTION)
                .correctAnswerIndex(CORRECT_ANSWER_INDEX)
                .answers(Arrays.asList(FIRST_ANSWER, SECOND_ANSWER, THIRD_ANSWER))
                .build();
        when(dao.getTestQuestions()).thenReturn(Collections.singletonList(testQuestion));
        List<TestQuestion> testQuestions = testQuestionService.getTestQuestions();
        assertNotNull(testQuestions);
        assertEquals(testQuestions.size(), 1);
        TestQuestion question = testQuestions.get(0);
        assertEquals(question, testQuestion);
    }
}