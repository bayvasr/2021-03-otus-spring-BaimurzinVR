package ru.otus.vbaymurzin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class TestQuestion {

    private final String question;
    private final int correctAnswerIndex;
    private final List<String> answers;

}
