package ru.otus.mylibrary.service;

import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto addBook(Book book);

    BookDto updateBook(Book book);

    void removeBook(long bookId);
}
