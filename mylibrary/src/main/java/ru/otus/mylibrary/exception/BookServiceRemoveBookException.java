package ru.otus.mylibrary.exception;

public class BookServiceRemoveBookException extends MyLibraryAppException {
    private static final long serialVersionUID = -4689176991544730063L;

    public BookServiceRemoveBookException(String message) {
        super(message);
    }
}
