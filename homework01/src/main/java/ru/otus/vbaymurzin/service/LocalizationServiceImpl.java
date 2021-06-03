package ru.otus.vbaymurzin.service;

import lombok.Getter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LocalizationServiceImpl implements LocalizationService {

    public static final String BASE_NAME = "message";
    private final List<Locale> availableLocales = new ArrayList<>();
    private ResourceBundle resourceBundle;

    public LocalizationServiceImpl(String defaultLanguageTag) {
        try {
            Locale defaultLocale = StringUtils.parseLocale(defaultLanguageTag);
            if (defaultLocale == null) {
                throw new IllegalArgumentException("No bundle for resource " + defaultLanguageTag);
            }
            Locale.setDefault(defaultLocale);
            resourceBundle = ResourceBundle.getBundle(BASE_NAME, defaultLocale);
        } catch (IllegalArgumentException e) {
            resourceBundle = ResourceBundle.getBundle(BASE_NAME);
        }

        availableLocales.addAll(Arrays.stream(Locale.getAvailableLocales())
                .map(locale -> ResourceBundle.getBundle(BASE_NAME, locale))
                .distinct()
                .map(ResourceBundle::getLocale)
                .collect(Collectors.toList()));
    }

    @Override
    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    @Override
    public void changeLocale(int index) {
        try {
            resourceBundle = ResourceBundle.getBundle(BASE_NAME, availableLocales.get(index));
        } catch (IndexOutOfBoundsException e) {
            resourceBundle = ResourceBundle.getBundle(BASE_NAME, resourceBundle.getLocale());
        }
    }

    @Override
    public Locale getCurrentLocale() {
        return resourceBundle.getLocale();
    }

}
