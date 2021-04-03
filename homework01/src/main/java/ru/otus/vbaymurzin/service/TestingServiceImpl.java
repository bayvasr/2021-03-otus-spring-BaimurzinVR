package ru.otus.vbaymurzin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.vbaymurzin.domain.TestQuestion;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {

    @Value("${min-scores}")
    private int minScores;

    private static final int DELIMITER_LINE_CHAR_REPEAT_COUNT = 80;
    private final TestQuestionService testQuestionService;


    private boolean checkAnswer(TestQuestion testQuestion, int answerNum) {
        //не очень удачно похоже хранить индекс правильного ответа, возможно нужно хранить номер правильного ответа
        return testQuestion.getCorrectAnswerIndex() == answerNum - 1;
    }

    private String getCorrectAnswer(TestQuestion testQuestion) {
        return testQuestion.getAnswers().get(testQuestion.getCorrectAnswerIndex());
    }

    private void testingResult(int scores, PrintStream printStream) {
        printStream.println("Testing completed!");
        String result = scores >= minScores ? "Congratulations! You passed the test." : "We sympathize, but you did not pass the test.";
        printStream.println(result + "You scored points is " + scores);

    }

    @Override
    public void startTesting(InputStream inputStream, PrintStream printStream) {
        Scanner scanner = new Scanner(inputStream);
        printStream.println("Enter your name:");
        String studentName = scanner.nextLine();
        printStream.println("Hello, " + studentName + "!");
        printStream.println("Minimum passing test score: " + minScores);
        printStream.print("Press enter to start testing");
        scanner.nextLine();
        AtomicInteger questionNum = new AtomicInteger(0);
        AtomicInteger scores = new AtomicInteger();
        testQuestionService.getTestQuestions().forEach(
                testQuestion ->
                {
                    printStream.println(questionNum.incrementAndGet() + ". Question: " + testQuestion.getQuestion() +
                            "\nSelect answer number:");
                    StringBuilder stringBuilder = new StringBuilder();
                    AtomicInteger answerNum = new AtomicInteger(0);
                    testQuestion.getAnswers().forEach(s -> stringBuilder.append(answerNum.incrementAndGet()).append(". ").append(s).append("\n"));
                    printStream.println(stringBuilder.toString());

                    int s = -1;
                    boolean loop = true;
                    while (loop) {
                        printStream.print("Answer number is: ");
                        loop = !scanner.hasNextInt();
                        if (loop) {
                            scanner.next();
                            printStream.println("Please, enter number of answer!");
                        } else {
                            s = scanner.nextInt();
                        }
                    }

                    printStream.println("-".repeat(DELIMITER_LINE_CHAR_REPEAT_COUNT));
                    if (checkAnswer(testQuestion, s)) {
                        printStream.println("Yeah, this is the correct answer!");
                        scores.incrementAndGet();
                    } else {
                        printStream.println("Bad, this is the wrong answer! The correct answer is " +
                                getCorrectAnswer(testQuestion));
                    }
                    printStream.println("=".repeat(DELIMITER_LINE_CHAR_REPEAT_COUNT) + "\n");
                }
        );

        testingResult(scores.get(), printStream);
    }


}
