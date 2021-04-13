package ru.otus.vbaymurzin.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.vbaymurzin.domain.TestQuestion;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestingTerminalServiceConsoleImplTest {

    @Mock
    private Scanner scanner;
    @Mock
    private PrintStream printStream;
    @Mock
    private LocalizationService localizationService;

    @InjectMocks
    private TestingTerminalServiceConsoleImpl serviceConsole;

    @Test
    void printQuestionWithAnswerOptions() {
        serviceConsole.printQuestionWithAnswerOptions(
                TestQuestion.builder()
                        .answers(new ArrayList<>())
                        .build()
        );
        verify(printStream, atLeastOnce()).println(anyString());
        verify(scanner, never()).next();
        verify(scanner, never()).nextLine();
    }

    @Test
    void enterStudentName() {
        when(scanner.nextLine()).thenReturn("test student");
        when(localizationService.getString(anyString())).thenReturn("Hello {0}");
        String studentName = serviceConsole.enterStudentName();
        assertThat(studentName).isEqualTo("test student");
    }

    @Test
    void printStartTesting() {
        when(localizationService.getString(anyString())).thenReturn("start ");
        serviceConsole.printStartTesting(2);
        verify(printStream, times(1)).println("start 2");
    }

    @Test
    void printIncorrect() {
        when(localizationService.getString(anyString())).thenReturn("answer ");
        serviceConsole.printIncorrect("incorrect");
        verify(printStream, times(1)).println("answer incorrect");
    }

    @Test
    void printCorrect() {
        when(localizationService.getString(anyString())).thenReturn("answer correct!!!");
        serviceConsole.printCorrect();
        verify(printStream, times(1)).println("answer correct!!!");
    }

    @Test
    void printSuccessComplete() {
        when(localizationService.getString(anyString())).thenReturn("Complete {0}");
        serviceConsole.printSuccessComplete(5);
        verify(printStream, times(1)).println("Complete 5");
    }

    @Test
    void printFailComplete() {
        when(localizationService.getString(anyString())).thenReturn("Fail, scores {0}");
        serviceConsole.printFailComplete(2);
        verify(printStream, times(1)).println("Fail, scores 2");
    }

    @Test
    void chooseLocale() {
        when(localizationService.getAvailableLocales()).thenReturn(new ArrayList<>());
        when(scanner.hasNextInt()).thenReturn(true);
        when(scanner.nextInt()).thenReturn(1);
        serviceConsole.chooseLocale();
        verify(localizationService, times(1)).getAvailableLocales();
        verify(localizationService, times(1)).changeLocale(0);
    }

    @Test
    void chooseNumberOption() {
        when(scanner.hasNextInt()).thenReturn(true);
        when(scanner.nextInt()).thenReturn(1);
        int numberOption = serviceConsole.chooseNumberOption();
        assertThat(numberOption).isEqualTo(1);
    }
}