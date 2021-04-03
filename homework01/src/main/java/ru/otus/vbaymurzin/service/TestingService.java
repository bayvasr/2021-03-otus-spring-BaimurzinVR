package ru.otus.vbaymurzin.service;

import java.io.InputStream;
import java.io.PrintStream;

public interface TestingService {
    void startTesting(InputStream inputStream, PrintStream printStream);
}
