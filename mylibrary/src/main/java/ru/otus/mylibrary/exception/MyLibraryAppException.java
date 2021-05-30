package ru.otus.mylibrary.exception;

public class MyLibraryAppException extends RuntimeException {
    private static final long serialVersionUID = 2596369759187274606L;

    public MyLibraryAppException(String message) {
        super(message);
    }

}
