package ru.otus.mylibrary.exception;

public class BookServiceUpdateBookException extends MyLibraryAppException {

    private static final long serialVersionUID = 2114769034894087039L;

    public BookServiceUpdateBookException(String message) {
        super(message);
    }
}
