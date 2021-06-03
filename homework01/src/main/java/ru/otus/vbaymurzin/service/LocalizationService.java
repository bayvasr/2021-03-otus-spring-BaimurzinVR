package ru.otus.vbaymurzin.service;

import java.util.List;
import java.util.Locale;

public interface LocalizationService {

    String getString(String key);

    void changeLocale(int index);

    Locale getCurrentLocale();

    List<Locale> getAvailableLocales();

}
