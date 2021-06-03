package ru.otus.vbaymurzin.dao;

import ru.otus.vbaymurzin.domain.TestQuestion;

import java.util.List;
import java.util.Locale;

public interface TestQuestionDao {
    default void reload() {
    }

    default void setLocale(Locale locale) {
    }

    List<TestQuestion> getTestQuestions();
}
