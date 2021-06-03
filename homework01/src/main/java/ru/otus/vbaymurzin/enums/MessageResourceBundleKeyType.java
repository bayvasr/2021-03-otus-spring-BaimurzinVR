package ru.otus.vbaymurzin.enums;

import lombok.Getter;

@Getter
public enum MessageResourceBundleKeyType {
    SELECT_LANG("select-language"),
    ENTER_NUMBER("enter-number"),
    THANKS("thanks"),
    QUESTION("question-label"),
    SELECT_ANSWER("select-answer-label"),
    ENTER_NAME("enter-name"),
    HELLO("hello-text"),
    MIN_SCORE("min-score-text"),
    START_TESTING("start-testing"),
    INCORRECT_ANSWER("incorrect-answer-label"),
    CORRECT_ANSWER("correct-answer-label"),
    TESTING_COMPLETED("testing-completed-label"),
    TESTING_NOT_COMPLETED("testing-not-completed-label"),
    DEFAULT_LANG("default-language");

    private String key;

    MessageResourceBundleKeyType(String key) {
        this.key = key;
    }
}
