package ru.otus.mylibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Comment;
import ru.otus.mylibrary.dto.BookDto;
import ru.otus.mylibrary.dto.CommentDto;
import ru.otus.mylibrary.dto.DtoConverter;
import ru.otus.mylibrary.exception.*;
import ru.otus.mylibrary.repositories.AuthorRepository;
import ru.otus.mylibrary.repositories.BookRepository;
import ru.otus.mylibrary.repositories.GenreRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentService commentService;

    private final DtoConverter<BookDto, Book> dtoConverter;
    private final DtoConverter<CommentDto, Comment> commentDtoConverter;

    private final ExampleMatcher bookMatcher = ExampleMatcher.matching()
            .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
            .withMatcher("author_id", ExampleMatcher.GenericPropertyMatchers.exact())
            .withIgnorePaths("id", "genre");

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

        if (bookRepository.exists(Example.of(book, bookMatcher))) {
            throw new BookServiceAddBookException(MessageFormat.format("The book \"{0}\" is already exists in library", bookDto.getTitle()));
        }

        saveAuthor(book);
        saveGenre(book);

        return dtoConverter.from(bookRepository.save(book));

    }

    private void saveGenre(Book book) {
        if (book.getGenre() != null) {
            book.setGenre(genreRepository.save(book.getGenre()));
        }
    }

    private void saveAuthor(Book book) {
        if (book.getAuthor() != null) {
            book.setAuthor(authorRepository.save(book.getAuthor()));
        }
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

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getBookComments(long bookId) {
        List<Comment> comments = bookRepository.findById(bookId)
                .map(Book::getComments)
                .orElseThrow(() -> new BookServiceFindBookException(MessageFormat.format("The book with ID {0} was not found in library.", bookId)));
        return comments.stream().map(commentDtoConverter::from).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CommentDto> addBookComment(long bookId, String comment) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookServiceFindBookException(MessageFormat.format("The book with ID {0} was not found in library.", bookId)));

        book.getComments().add(Comment.builder().id(0).text(comment).book(book).build());
        bookRepository.save(book);
        return book.getComments().stream().map(commentDtoConverter::from).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CommentDto> removeBookComment(long bookId, long commentId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookServiceFindBookException(MessageFormat.format("The book with ID {0} was not found in library.", bookId)));

        Comment foundComment = book.getComments().stream()
                .filter(comment -> comment.getId() == commentId)
                .findFirst()
                .orElseThrow(() -> new CommentServiceNotFoundException(MessageFormat.format("The comment with ID {0} was not found for book {1}.", commentId, book.getTitle())));
        book.getComments().remove(foundComment);
        commentService.removeComment(foundComment.getId());

        Book savedBook = bookRepository.save(book);
        return savedBook.getComments().stream().map(commentDtoConverter::from).collect(Collectors.toList());
    }
}
