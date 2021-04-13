package ru.otus.vbaymurzin.service;

import lombok.RequiredArgsConstructor;
import ru.otus.vbaymurzin.domain.TestQuestion;
import ru.otus.vbaymurzin.enums.MessageResourceBundleKeyType;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.otus.vbaymurzin.enums.MessageResourceBundleKeyType.*;

@RequiredArgsConstructor
public class TestingTerminalServiceConsoleImpl implements TestingTerminalService {
    private static final int DELIMITER_LINE_CHAR_REPEAT_COUNT = 80;
    private final Scanner scanner;
    private final PrintStream printStream;
    private final LocalizationService localizationService;

    private int questionNum = 0;

    private void printAvailableLanguages() {
        AtomicInteger num = new AtomicInteger(0);
        localizationService.getAvailableLocales().forEach(locale ->
                printStream.println(num.incrementAndGet() + ". " +
                        (locale.equals(Locale.ROOT) ? localizationService.getString(DEFAULT_LANG.getKey()) :
                                locale.getDisplayLanguage())));

    }

    @Override
    public void printQuestionWithAnswerOptions(TestQuestion testQuestion) {
        questionNum++;
        printStream.println("-".repeat(DELIMITER_LINE_CHAR_REPEAT_COUNT));
        printStream.println(questionNum + ". " +
                localizationService.getString(QUESTION.getKey()) +
                testQuestion.getQuestion() +
                "\n" + localizationService.getString(SELECT_ANSWER.getKey()));
        StringBuilder stringBuilder = new StringBuilder();
        AtomicInteger answerNum = new AtomicInteger(0);
        testQuestion.getAnswers().forEach(s -> stringBuilder.append(answerNum.incrementAndGet()).append(". ").append(s).append("\n"));
        printStream.println(stringBuilder.toString());
    }

    @Override
    public String enterStudentName() {
        printStream.println(localizationService.getString(ENTER_NAME.getKey()));
        String inputString = scanner.nextLine();
        printStream.println(
                MessageFormat.format(
                        localizationService.getString(HELLO.getKey()), inputString));
        return inputString;
    }

    @Override
    public void printStartTesting(int minScores) {
        printStream.println(localizationService.getString(MIN_SCORE.getKey()) + minScores);
        printStream.print(localizationService.getString(START_TESTING.getKey()));
        scanner.nextLine();
    }

    @Override
    public void printIncorrect(String correctAnswer) {
        printStream.println("-".repeat(DELIMITER_LINE_CHAR_REPEAT_COUNT));
        printStream.println(localizationService.getString(INCORRECT_ANSWER.getKey()) + correctAnswer);
        printStream.println("=".repeat(DELIMITER_LINE_CHAR_REPEAT_COUNT) + "\n");
    }

    @Override
    public void printCorrect() {
        printStream.println("-".repeat(DELIMITER_LINE_CHAR_REPEAT_COUNT));
        printStream.println(localizationService.getString(CORRECT_ANSWER.getKey()));
        printStream.println("=".repeat(DELIMITER_LINE_CHAR_REPEAT_COUNT) + "\n");
    }

    @Override
    public void printSuccessComplete(int scores) {
        printStream.println(MessageFormat.format(
                localizationService.getString(TESTING_COMPLETED.getKey()), scores));
    }

    @Override
    public void printFailComplete(int scores) {
        printStream.println(MessageFormat.format(
                localizationService.getString(TESTING_NOT_COMPLETED.getKey()), scores));
    }

    @Override
    public Locale chooseLocale() {
        printStream.println(localizationService.getString(MessageResourceBundleKeyType.SELECT_LANG.getKey()));
        printAvailableLanguages();
        printStream.println(localizationService.getString(MessageResourceBundleKeyType.ENTER_NUMBER.getKey()));
        localizationService.changeLocale(getInputInteger() - 1);
        printStream.println(localizationService.getString(THANKS.getKey()));
        printStream.println("");
        return localizationService.getCurrentLocale();
    }

    @Override
    public int chooseNumberOption() {
        return getInputInteger();
    }

    private int getInputInteger() {
        int result = -1;
        boolean loop = true;
        while (loop) {
            loop = !scanner.hasNextInt();
            if (loop) {
                printStream.println(localizationService.getString(MessageResourceBundleKeyType.ENTER_NUMBER.getKey()));
                scanner.next();
            } else {
                result = scanner.nextInt();
                scanner.nextLine();
            }
        }
        return result;
    }
}
