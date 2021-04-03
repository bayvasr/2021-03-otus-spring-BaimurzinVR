package ru.otus.vbaymurzin.dao;

import org.springframework.core.io.Resource;
import ru.otus.vbaymurzin.domain.TestQuestion;
import ru.otus.vbaymurzin.exceptions.InvalidCSVResourceException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// бин определим в конфигурацонном классе, причина описана там
public class TestQuestionDaoCSV implements TestQuestionDao {

    private static final String COMMA_DELIMITER = ",";
    private static final String SEMICOLON_DELIMITER = ";";
    private final List<TestQuestion> testQuestions = new ArrayList<>(0);

    public TestQuestionDaoCSV(Resource csvResource) {
        if (csvResource == null) {
            throw new InvalidCSVResourceException("Error get resource: resource is null");
        }
        loadTestQuestions(csvResource);
    }

    private void loadTestQuestions(Resource csvResource) {
        try {
            InputStream inputStream = csvResource.getInputStream();
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


    public List<TestQuestion> getTestQuestions() {
        return testQuestions;
    }
}
