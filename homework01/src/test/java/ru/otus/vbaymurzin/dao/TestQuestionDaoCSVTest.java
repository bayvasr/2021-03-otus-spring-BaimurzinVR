package ru.otus.vbaymurzin.dao;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import ru.otus.vbaymurzin.domain.TestQuestion;
import ru.otus.vbaymurzin.exceptions.InvalidCSVResourceException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestQuestionDaoCSVTest {

    private TestQuestionDaoCSV daoCSV;

    @Test
    void getTestQuestions() throws InvalidCSVResourceException {
        ByteArrayResource byteArrayResource = new ByteArrayResource("1+2*3=?;9,6,7,5;2\nEarth is round?;Yes, No;0".getBytes());
        daoCSV = new TestQuestionDaoCSV(byteArrayResource);
        List<TestQuestion> testQuestions = daoCSV.getTestQuestions();
        assertNotNull(testQuestions);
        assertEquals(testQuestions.size(), 2);
        TestQuestion testQuestion = testQuestions.get(0);
        assertEquals(testQuestion.getQuestion(), "1+2*3=?");
        assertEquals(testQuestion.getCorrectAnswerIndex(), 2);
        assertEquals(testQuestion.getAnswers().size(), 4);
    }

    @Test
    void getTestQuestionsInvalidCSV() {
        ByteArrayResource byteArrayResource = new ByteArrayResource("1+2*3=?*9,6,7,5;2\nEarth is round?;YesNo;0".getBytes());
        assertThrows(InvalidCSVResourceException.class, () -> daoCSV = new TestQuestionDaoCSV(byteArrayResource));
    }

    @Test
    void getTestQuestionsInvalidCorrectAnswerIndexCSV() {
        ByteArrayResource byteArrayResource = new ByteArrayResource("1+2*3=?*9,6,7,5;dd\nEarth is round?;YesNo;0".getBytes());
        assertThrows(InvalidCSVResourceException.class, () -> daoCSV = new TestQuestionDaoCSV(byteArrayResource));
    }

    @Test
    void getTestQuestionsEmptyCSV() throws InvalidCSVResourceException {
        ByteArrayResource byteArrayResource = new ByteArrayResource("".getBytes());
        daoCSV = new TestQuestionDaoCSV(byteArrayResource);
        List<TestQuestion> testQuestions = daoCSV.getTestQuestions();
        assertNotNull(testQuestions);
        assertTrue(testQuestions.isEmpty());
    }

    @Test
    void getTestQuestionsNullCSV() {
        assertThrows(InvalidCSVResourceException.class, () -> daoCSV = new TestQuestionDaoCSV(null));
    }
}