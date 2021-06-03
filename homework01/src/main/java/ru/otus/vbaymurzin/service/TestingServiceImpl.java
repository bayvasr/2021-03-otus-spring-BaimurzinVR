package ru.otus.vbaymurzin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.vbaymurzin.config.ApplicationConfiguration;
import ru.otus.vbaymurzin.domain.TestQuestion;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {

    private final TestQuestionService testQuestionService;
    private final TestingTerminalService terminalService;
    private final LocalizationService localizationService;
    private final ApplicationConfiguration configuration;


    private boolean checkAnswer(TestQuestion testQuestion, int answerNum) {
        //не очень удачно похоже хранить индекс правильного ответа, возможно нужно хранить номер правильного ответа
        return testQuestion.getCorrectAnswerIndex() == answerNum - 1;
    }

    private String getCorrectAnswer(TestQuestion testQuestion) {
        return testQuestion.getAnswers().get(testQuestion.getCorrectAnswerIndex());
    }

    private void testingResult(int scores) {
        if (scores >= configuration.getMinScores()) {
            terminalService.printSuccessComplete(scores);
        } else {
            terminalService.printFailComplete(scores);
        }

    }

    @Override
    public void startTesting() {
        terminalService.chooseLocale();
        testQuestionService.setLocale(localizationService.getCurrentLocale());

        terminalService.enterStudentName();

        terminalService.printStartTesting(configuration.getMinScores());

        AtomicInteger scores = new AtomicInteger();
        testQuestionService.getTestQuestions().forEach(
                testQuestion ->
                {
                    terminalService.printQuestionWithAnswerOptions(testQuestion);
                    int s = terminalService.chooseNumberOption();

                    if (checkAnswer(testQuestion, s)) {
                        terminalService.printCorrect();
                        scores.incrementAndGet();
                    } else {
                        terminalService.printIncorrect(getCorrectAnswer(testQuestion));
                    }
                }
        );

        testingResult(scores.get());
    }


}
