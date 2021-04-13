package ru.otus.vbaymurzin.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.vbaymurzin.config.ApplicationConfiguration;

import java.util.ArrayList;
import java.util.Locale;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestingServiceImplTest {
    @Mock
    TestQuestionService questionService;
    @Mock
    LocalizationService localizationService;
    @Mock
    private ApplicationConfiguration configuration;
    @Mock
    private TestingTerminalService terminalService;
    ;
    @InjectMocks
    private TestingServiceImpl testingService;

    // вот здесь я понял... что класс не совсем удачен, так как тестировать тяжело
    // значит Test First еще и помогает выстраивать правильные классы...
    @SneakyThrows
    @Test
    void startTesting() {
        when(questionService.getTestQuestions()).thenReturn(new ArrayList<>());
        when(localizationService.getCurrentLocale()).thenReturn(Locale.ROOT);
        testingService.startTesting();
        verify(terminalService, times(1)).enterStudentName();
        verify(terminalService, times(1)).chooseLocale();
    }

}