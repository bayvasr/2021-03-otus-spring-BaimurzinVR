package ru.otus.vbaymurzin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor
public class TestQuestion {

    private final String question;
    private final int correctAnswerIndex;
    private final List<String> answers;


    public List<String> getAnswers() {
        return answers;
    }

    public String getQuestion() {
        return question;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}
