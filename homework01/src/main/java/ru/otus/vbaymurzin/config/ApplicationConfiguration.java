package ru.otus.vbaymurzin.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationConfiguration {
    private String questionsCsvFileName;
    private int minScores;
    private String defaultLanguage;

}
