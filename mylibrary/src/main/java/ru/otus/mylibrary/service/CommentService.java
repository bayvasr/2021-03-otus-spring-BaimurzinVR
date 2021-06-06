package ru.otus.mylibrary.service;

import ru.otus.mylibrary.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto addComment(long bookId, String text);

    CommentDto updateComment(long commentId, String text);

    void removeComment(long commentId);

    void clearComments(long bookId);

    List<CommentDto> getComments(long bookId);
}
