package ru.otus.mylibrary.service;

import ru.otus.mylibrary.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto addBook(BookDto book);

    BookDto updateBook(BookDto book);

    void removeBook(long bookId);

    BookDto getBook(long bookId);
}
