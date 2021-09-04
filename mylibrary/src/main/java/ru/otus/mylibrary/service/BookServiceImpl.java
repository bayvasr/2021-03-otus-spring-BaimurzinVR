package ru.otus.mylibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.dto.BookDto;
import ru.otus.mylibrary.dto.DtoConverter;
import ru.otus.mylibrary.exception.BookServiceAddBookException;
import ru.otus.mylibrary.exception.BookServiceFindBookException;
import ru.otus.mylibrary.exception.BookServiceRemoveBookException;
import ru.otus.mylibrary.exception.BookServiceUpdateBookException;
import ru.otus.mylibrary.repositories.BookRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final DtoConverter<BookDto, Book> dtoConverter;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(dtoConverter::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDto addBook(BookDto bookDto) {
        Book book = dtoConverter.to(bookDto);

        if (bookRepository.exists(Example.of(book))) {
            throw new BookServiceAddBookException(MessageFormat.format("The book \"{0}\" is already exists in library", bookDto.getTitle()));
        }
        return dtoConverter.from(bookRepository.save(book));

    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto bookDto) {
        if (!bookRepository.existsById(bookDto.getId())) {
            throw new BookServiceUpdateBookException(MessageFormat.format("The book  with ID {0} was not found in library", bookDto.getId()));
        }
        Book book = dtoConverter.to(bookDto);
        return dtoConverter.from(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void removeBook(long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookServiceRemoveBookException(MessageFormat.format("The book with ID {0} was not found in library.", bookId));
        }
        bookRepository.deleteById(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getBook(long bookId) {
        return bookRepository
                .findById(bookId)
                .map(dtoConverter::from)
                .orElseThrow(() -> new BookServiceFindBookException(MessageFormat.format("The book with ID {0} was not found in library.", bookId)));
    }
}
