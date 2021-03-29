package ru.otus.vbaymurzin.dao;

import ru.otus.vbaymurzin.domain.TestQuestion;

import java.util.List;

public interface TestQuestionDao {

    List<TestQuestion> getTestQuestions();
}
