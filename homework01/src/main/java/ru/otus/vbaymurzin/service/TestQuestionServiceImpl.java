package ru.otus.vbaymurzin.service;

import org.springframework.stereotype.Service;
import ru.otus.vbaymurzin.dao.TestQuestionDao;
import ru.otus.vbaymurzin.domain.TestQuestion;

import java.util.List;
import java.util.Locale;

@Service
public class TestQuestionServiceImpl implements TestQuestionService {

    private final TestQuestionDao dao;

    public TestQuestionServiceImpl(TestQuestionDao dao) {
        this.dao = dao;
    }

    public List<TestQuestion> getTestQuestions() {
        return dao.getTestQuestions();
    }

    @Override
    public void setLocale(Locale locale) {
        dao.setLocale(locale);
    }
}
