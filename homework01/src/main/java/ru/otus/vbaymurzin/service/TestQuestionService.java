package ru.otus.vbaymurzin.service;

import ru.otus.vbaymurzin.domain.TestQuestion;

import java.util.List;
import java.util.Locale;

public interface TestQuestionService {

    List<TestQuestion> getTestQuestions();

    void setLocale(Locale locale);
}
