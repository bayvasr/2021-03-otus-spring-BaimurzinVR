package ru.otus.vbaymurzin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.otus.vbaymurzin.dao.TestQuestionDao;
import ru.otus.vbaymurzin.dao.TestQuestionDaoCSV;

// Вопрос: нужно ли писать тестя на конфигурационные классы?
@Configuration
@PropertySource("application.properties")
@RequiredArgsConstructor
public class AppConfig {
    @Value("${questions-csv}")
    String csvFileName;

    private final ResourceLoader resourceLoader;

    // как раз сэмулировалоась ситуация, когда не можем поменять класс, чтобы он принимал имя файла, а не ресурс
    // можно было бы, имя файла сразу передавать в конструктор класса и тогда мы бы его аннотировали как @Repository
    @Bean
    public TestQuestionDao testQuestionDaoCSV() {
        Resource resource = resourceLoader.getResource(csvFileName);
        return new TestQuestionDaoCSV(resource);
    }
}
