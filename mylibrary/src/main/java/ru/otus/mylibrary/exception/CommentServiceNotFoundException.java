package ru.otus.mylibrary.exception;

public class CommentServiceNotFoundException extends MyLibraryAppException {

    private static final long serialVersionUID = 2449690131641129632L;

    public CommentServiceNotFoundException(String message) {
        super(message);
    }
}
