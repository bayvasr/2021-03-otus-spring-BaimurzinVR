package ru.otus.mylibrary.service;

import ru.otus.mylibrary.dto.BookDto;
import ru.otus.mylibrary.dto.CommentDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto addBook(BookDto book);

    BookDto updateBook(BookDto book);

    void removeBook(long bookId);

    BookDto getBook(long bookId);

    List<CommentDto> getBookComments(long bookId);

    List<CommentDto> addBookComment(long bookId, String comment);

    List<CommentDto> removeBookComment(long bookId, long commentId);
}
