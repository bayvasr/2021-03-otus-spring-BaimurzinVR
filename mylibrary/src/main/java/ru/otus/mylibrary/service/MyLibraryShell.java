package ru.otus.mylibrary.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;
import ru.otus.mylibrary.config.AppConfig;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.BookDto;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class MyLibraryShell {
    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final MessageSource ms;
    private final AppConfig cfg;

    private static final Logger logger = LoggerFactory.getLogger(MyLibraryShell.class);

    @ShellMethod(key = {"books", "b"}, value = "Get the list of all books in library: \"Id\". \"Title\", \"Author\", \"Genre\"")
    public List<String> getAllBooks() {
        try {
            return bookService.getAllBooks()
                    .stream()
                    .map(BookDto::toString)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return List.of(ms.getMessage("books.getting.error", null, cfg.getLocale()));
    }

    @ShellMethod(key = {"add-book", "ab"}, value = "Add a book to library: ab \"Title\" \"Author\" \"Genre\" (Author and Genre are optional)")
    public String addBook(@ShellOption String bookTitle,
                          @ShellOption(defaultValue = "") String authorName,
                          @ShellOption(defaultValue = "") String genreName) {
        try {
            Genre genre = StringUtils.hasText(genreName) ?
                    genreService.saveGenre(new Genre(genreName)) :
                    Genre.UNKNOWN_GENRE;
            Author author = StringUtils.hasText(authorName) ?
                    authorService.saveAuthor(new Author(authorName)) :
                    Author.UNKNOWN_AUTHOR;
            BookDto book = bookService.addBook(new Book(bookTitle, author, genre));
            return ms.getMessage("book.added.successfully", new String[]{book.toString()}, cfg.getLocale());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ms.getMessage("book.adding.error", null, cfg.getLocale());
    }

    @ShellMethod(key = {"edit-book", "eb"}, value = "Edit a book by identifier: eb Identifier \"Title\" \"Author\" \"Genre\" (Author and Genre are optional)")
    public String editBook(@ShellOption long bookId,
                           @ShellOption String bookTitle,
                           @ShellOption(defaultValue = "") String authorName,
                           @ShellOption(defaultValue = "") String genreName) {
        try {
            Genre genre = StringUtils.hasText(genreName) ?
                    genreService.saveGenre(new Genre(genreName)) :
                    Genre.UNKNOWN_GENRE;
            Author author = StringUtils.hasText(authorName) ?
                    authorService.saveAuthor(new Author(authorName)) :
                    Author.UNKNOWN_AUTHOR;
            BookDto book = bookService.saveBook(new Book(bookId, bookTitle, author, genre));
            return ms.getMessage("book.edited.successfully", new String[]{book.toString()}, cfg.getLocale());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ms.getMessage("book.editing.error", null, cfg.getLocale());
    }

    @ShellMethod(key = {"remove-book", "rb"}, value = "Remove a book from library by identifier: rb Identifier")
    public String removeBook(@ShellOption long bookId) {
        try {
            bookService.removeBook(bookId);
            return ms.getMessage("book.removed.successfully", null, cfg.getLocale());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ms.getMessage("book.removing.error", null, cfg.getLocale());
    }

    @ShellMethod(key = {"authors", "a"}, value = "Get the list of all authors")
    public List<String> getAllAuthors() {
        try {
            return authorService.getAllAuthors();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return List.of(ms.getMessage("authors.getting.error", null, cfg.getLocale()));
    }

    @ShellMethod(key = {"add-author", "aa"}, value = "Add an author to library: aa \"Author\"")
    public String addAuthor(@ShellOption String authorName) {
        try {
            Author author = authorService.saveAuthor(new Author(authorName));
            return ms.getMessage("author.added.successfully", new String[]{author.toString()}, cfg.getLocale());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ms.getMessage("author.adding.error", null, cfg.getLocale());
    }

    @ShellMethod(key = {"genres", "g"}, value = "Get the list of all genres")
    public List<String> getAllGenres() {
        try {
            return genreService.getAllGenres();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return List.of(ms.getMessage("genres.getting.error", null, cfg.getLocale()));
    }

    @ShellMethod(key = {"add-genre", "ag"}, value = "Add a genre to library: ag \"Genre\"")
    public String addGenre(@ShellOption String genreName) {
        try {
            Genre genre = genreService.saveGenre(new Genre(genreName));
            return ms.getMessage("genre.added.successfully", new String[]{genre.toString()}, cfg.getLocale());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ms.getMessage("genre.adding.error", null, cfg.getLocale());
    }
}
