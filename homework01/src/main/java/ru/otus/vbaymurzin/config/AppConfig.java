package ru.otus.vbaymurzin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.otus.vbaymurzin.dao.TestQuestionDao;
import ru.otus.vbaymurzin.dao.TestQuestionDaoCSV;
import ru.otus.vbaymurzin.service.LocalizationService;
import ru.otus.vbaymurzin.service.LocalizationServiceImpl;
import ru.otus.vbaymurzin.service.TestingTerminalService;
import ru.otus.vbaymurzin.service.TestingTerminalServiceConsoleImpl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

@Configuration
public class AppConfig {

    private static final String DB_PATH = "classpath:/db/";

    @Autowired
    private ApplicationConfiguration configuration;

    @Bean
    public TestQuestionDao testQuestionDaoEngCSV(LocalizationService localizationService,
                                                 ResourceLoader resourceLoader) {

        Map<Locale, Resource> localeResourceMap = new HashMap<>();


        localizationService.getAvailableLocales().forEach(
                locale -> {
                    if (locale.equals(Locale.ROOT)) {
                        localeResourceMap.put(locale, resourceLoader.getResource(DB_PATH +
                                configuration.getQuestionsCsvFileName()));
                    } else {
                        localeResourceMap.put(locale, resourceLoader.getResource(
                                DB_PATH + locale.toLanguageTag() + "/" +
                                        configuration.getQuestionsCsvFileName()));
                    }
                }

        );

        return new TestQuestionDaoCSV(localeResourceMap);
    }

    @Bean
    public LocalizationService localizationService() {
        return new LocalizationServiceImpl(configuration.getDefaultLanguage());
    }

    @Bean
    public TestingTerminalService testingTerminalConsoleService(LocalizationService localizationService) {
        return new TestingTerminalServiceConsoleImpl(new Scanner(System.in), System.out, localizationService);
    }
}
