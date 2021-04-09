package ru.otus.vbaymurzin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import ru.otus.vbaymurzin.dao.TestQuestionDao;
import ru.otus.vbaymurzin.dao.TestQuestionDaoCSV;

// Вопрос: нужно ли писать тест на конфигурационные классы?
@Configuration
@PropertySource("application.properties")
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public TestQuestionDao testQuestionDaoCSV(@Value("${questions-csv}") Resource resource) {
        return new TestQuestionDaoCSV(resource);
    }
}
