package ru.otus.mylibrary.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "mylibrary")
public class AppConfig {
    private Locale locale;

    public void setDefaultLocaleTag(String localeTag) {
        setLocale(localeTag);
    }

    public void setLocale(String localeTag) {
        locale = Locale.forLanguageTag(localeTag);
    }
}
