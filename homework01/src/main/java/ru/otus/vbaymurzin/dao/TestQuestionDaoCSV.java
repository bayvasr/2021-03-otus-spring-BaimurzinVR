package ru.otus.vbaymurzin.dao;

import org.springframework.core.io.Resource;
import ru.otus.vbaymurzin.domain.TestQuestion;
import ru.otus.vbaymurzin.exceptions.InvalidCSVResourceException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

// бин определим в конфигурацонном классе, причина описана там
public class TestQuestionDaoCSV implements TestQuestionDao {

    private static final String COMMA_DELIMITER = ",";
    private static final String SEMICOLON_DELIMITER = ";";
    private final List<TestQuestion> testQuestions = new ArrayList<>(0);
    private Map<Locale, Resource> localeCSVResources;

    public TestQuestionDaoCSV(Map<Locale, Resource> localeCSVResources) {
        if (localeCSVResources == null) {
            throw new InvalidCSVResourceException("Error get resource: resource is null");
        }
        this.localeCSVResources = localeCSVResources;
        loadTestQuestions(Locale.getDefault());
    }

    private void loadTestQuestions(Locale language) {
        try {
            testQuestions.clear();
            InputStream inputStream = localeCSVResources
                    .getOrDefault(language, localeCSVResources.get(Locale.ROOT))
                    .getInputStream();
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
                while (scanner.hasNextLine()) {
                    testQuestions.add(getTestQuestionFromLine(scanner.nextLine()));
                }
            }
        } catch (Exception e) {
            throw new InvalidCSVResourceException("Error get resource: " + e.getMessage(), e);
        }

    }

    private TestQuestion getTestQuestionFromLine(String nextLine) {
        String[] fields = nextLine.split(SEMICOLON_DELIMITER);
        if (fields.length != 3) {
            throw new InvalidCSVResourceException("Invalid csv file, there must be three columns, but there are " + fields.length);
        }
        String[] answers = fields[1].split(COMMA_DELIMITER);
        int correctAnswerIndex;
        try {
            correctAnswerIndex = Integer.parseInt(fields[2]);
        } catch (NumberFormatException e) {
            throw new InvalidCSVResourceException("Error by parse correct answer index <" + fields[2] + ">: " + e.getMessage(), e);
        }
        return TestQuestion.builder()
                .question(fields[0])
                .answers(Arrays.stream(answers).map(String::trim).collect(Collectors.toList()))
                .correctAnswerIndex(correctAnswerIndex)
                .build();
    }

    @Override
    public void setLocale(Locale locale) {
        loadTestQuestions(locale);
    }

    public List<TestQuestion> getTestQuestions() {
        return testQuestions;
    }
}
