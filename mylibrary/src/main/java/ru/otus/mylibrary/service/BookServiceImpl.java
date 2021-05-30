package ru.otus.mylibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mylibrary.dao.BookDao;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.dto.BookDto;
import ru.otus.mylibrary.exception.BookServiceAddBookException;
import ru.otus.mylibrary.exception.BookServiceRemoveBookException;
import ru.otus.mylibrary.exception.BookServiceSaveBookException;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao dao;

    @Override
    public List<BookDto> getAllBooks() {
        return dao.getAll().stream()
                .map(BookDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto addBook(Book book) {
        if (dao.find(book).isPresent()) {
            throw new BookServiceAddBookException(MessageFormat.format("The book \"{0}\" is already exists in library", book.getTitle()));
        }
        return new BookDto(dao.insert(book));
    }

    @Override
    public BookDto saveBook(Book book) {
        if (dao.find(book).isEmpty()) {
            throw new BookServiceSaveBookException(MessageFormat.format("The book \"{0}\" not found in library", book.getTitle()));
        }
        dao.update(book);
        return new BookDto(book);
    }

    @Override
    public void removeBook(long bookId) {
        if (dao.getById(bookId).isEmpty()) {
            throw new BookServiceRemoveBookException(MessageFormat.format("The book with ID {0} was not found in the database.", bookId));
        }
        dao.deleteById(bookId);
    }
}
