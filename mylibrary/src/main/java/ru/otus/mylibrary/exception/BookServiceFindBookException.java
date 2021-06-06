package ru.otus.mylibrary.exception;

public class BookServiceFindBookException extends MyLibraryAppException {
    private static final long serialVersionUID = -4689176991544730063L;

    public BookServiceFindBookException(String message) {
        super(message);
    }
}
