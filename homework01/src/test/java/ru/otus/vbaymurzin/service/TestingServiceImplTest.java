package ru.otus.vbaymurzin.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestingServiceImplTest {
    @Mock
    TestQuestionService questionService;
    @InjectMocks
    private TestingServiceImpl testingService;
    @Mock
    private PrintStream output;
    @Mock
    private Scanner input;

    // вот здесь я понял... что класс не совсем удачен, так как тестировать тяжело
    // значит Test First еще и помогает выстраивать правильные классы...
    @SneakyThrows
    @Test
    void startTesting() {
        when(questionService.getTestQuestions()).thenReturn(new ArrayList<>());
        testingService.startTesting(input, output);
        verify(input, atLeastOnce()).nextLine();
        verify(output, atLeastOnce()).println(anyString());
    }

}