package ru.otus.vbaymurzin.service;

import ru.otus.vbaymurzin.domain.TestQuestion;

import java.util.Locale;

public interface TestingTerminalService {

    void printQuestionWithAnswerOptions(TestQuestion testQuestion);

    String enterStudentName();

    void printStartTesting(int minScores);

    void printIncorrect(String correctAnswer);

    void printCorrect();

    void printSuccessComplete(int scores);

    void printFailComplete(int scores);

    Locale chooseLocale();

    int chooseNumberOption();

}
