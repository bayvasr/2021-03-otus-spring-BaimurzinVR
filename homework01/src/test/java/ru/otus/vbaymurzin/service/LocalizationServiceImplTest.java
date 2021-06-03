package ru.otus.vbaymurzin.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.vbaymurzin.enums.MessageResourceBundleKeyType;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

class LocalizationServiceImplTest {

    private LocalizationServiceImpl localizationService;

    @BeforeEach
    void setUp() {
        localizationService = new LocalizationServiceImpl("ru-RU");
    }

    @Test
    void getString() {
        ResourceBundle resourceBundle = localizationService.getResourceBundle();
        String resourceBundleString = resourceBundle.getString(MessageResourceBundleKeyType.HELLO.getKey());
        String localizationServiceString = localizationService.getString(MessageResourceBundleKeyType.HELLO.getKey());
        assertThat(localizationServiceString).isNotEmpty().isEqualTo(resourceBundleString);

    }

    @Test
    void changeLocale() {
        localizationService.changeLocale(0);
        Locale currentLocaleBefore = localizationService.getCurrentLocale();
        Locale firstLocale = localizationService.getAvailableLocales().get(1);
        localizationService.changeLocale(1);
        Locale currentLocaleAfter = localizationService.getCurrentLocale();
        assertThat(currentLocaleAfter).isNotEqualTo(currentLocaleBefore).isEqualTo(firstLocale);

    }

    @Test
    void getCurrentLocale() {
        Locale currentLocale = localizationService.getCurrentLocale();
        assertThat(currentLocale).isNotNull();
        assertThat(currentLocale.getLanguage()).isEqualTo("ru");
    }

    @Test
    void getAvailableLocales() {
        List<Locale> availableLocales = localizationService.getAvailableLocales();
        assertThat(availableLocales).isNotNull().isNotEmpty();
    }

    @Test
    void getResourceBundle() {
        ResourceBundle resourceBundle = localizationService.getResourceBundle();
        assertThat(resourceBundle).isNotNull();
    }
}