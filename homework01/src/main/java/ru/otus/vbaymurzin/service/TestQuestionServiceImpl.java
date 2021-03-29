package ru.otus.vbaymurzin.service;

import ru.otus.vbaymurzin.dao.TestQuestionDao;
import ru.otus.vbaymurzin.domain.TestQuestion;

import java.util.List;

public class TestQuestionServiceImpl implements TestQuestionService {

    private final TestQuestionDao dao;

    public TestQuestionServiceImpl(TestQuestionDao dao) {
        this.dao = dao;
    }

    public List<TestQuestion> getTestQuestions() {
        return dao.getTestQuestions();
    }
}
