package ru.otus.vbaymurzin.dao;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import ru.otus.vbaymurzin.domain.TestQuestion;
import ru.otus.vbaymurzin.exceptions.InvalidCSVResourceException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class TestQuestionDaoCSVTest {

    private TestQuestionDaoCSV daoCSV;

    @Test
    void getTestQuestions() throws InvalidCSVResourceException {
        ByteArrayResource byteArrayResource = new ByteArrayResource("1+2*3=?;9,6,7,5;2\nEarth is round?;Yes, No;0".getBytes());
        daoCSV = new TestQuestionDaoCSV(Collections.singletonMap(Locale.getDefault(), byteArrayResource));
        List<TestQuestion> testQuestions = daoCSV.getTestQuestions();
        assertNotNull(testQuestions);
        assertEquals(testQuestions.size(), 2);
    }

    @Test
    void getTestQuestionsEmptyCSV() throws InvalidCSVResourceException {
        ByteArrayResource byteArrayResource = new ByteArrayResource("".getBytes());
        daoCSV = new TestQuestionDaoCSV(Collections.singletonMap(Locale.getDefault(), byteArrayResource));
        List<TestQuestion> testQuestions = daoCSV.getTestQuestions();
        assertNotNull(testQuestions);
        assertTrue(testQuestions.isEmpty());
    }
}