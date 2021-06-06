package ru.otus.mylibrary.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;
import ru.otus.mylibrary.config.AppConfig;
import ru.otus.mylibrary.dto.AuthorDto;
import ru.otus.mylibrary.dto.BookDto;
import ru.otus.mylibrary.dto.CommentDto;
import ru.otus.mylibrary.dto.GenreDto;
import ru.otus.mylibrary.service.AuthorService;
import ru.otus.mylibrary.service.BookService;
import ru.otus.mylibrary.service.CommentService;
import ru.otus.mylibrary.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class MyLibraryShellController {
    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final CommentService commentService;
    private final MessageSource ms;
    private final AppConfig cfg;

    private static final Logger logger = LoggerFactory.getLogger(MyLibraryShellController.class);

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
            GenreDto genre = StringUtils.hasText(genreName) ?
                    genreService.saveGenre(GenreDto.builder().name(genreName).build()) :
                    GenreDto.builder().build();
            AuthorDto author = StringUtils.hasText(authorName) ?
                    authorService.saveAuthor(AuthorDto.builder().name(authorName).build()) :
                    AuthorDto.builder().build();
            BookDto book = bookService.addBook(
                    BookDto.builder()
                            .title(bookTitle)
                            .authorId(author.getId())
                            .author(author.getName())
                            .genreId(genre.getId())
                            .genre(genre.getName())
                            .comments(new ArrayList<>())
                            .build());
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
            GenreDto genre = StringUtils.hasText(genreName) ?
                    genreService.saveGenre(GenreDto.builder().name(genreName).build()) :
                    GenreDto.builder().build();
            AuthorDto author = StringUtils.hasText(authorName) ?
                    authorService.saveAuthor(AuthorDto.builder().name(authorName).build()) :
                    AuthorDto.builder().build();
            BookDto book = bookService.updateBook(BookDto.builder()
                    .id(bookId)
                    .title(bookTitle)
                    .authorId(author.getId())
                    .author(author.getName())
                    .genreId(genre.getId())
                    .genre(genre.getName())
                    .build());
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
            AuthorDto author = authorService.saveAuthor(AuthorDto.builder().name(authorName).build());
            return ms.getMessage("author.added.successfully", new String[]{author.getName(), String.valueOf(author.getId())}, cfg.getLocale());
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
            GenreDto genre = genreService.saveGenre(GenreDto.builder().name(genreName).build());
            return ms.getMessage("genre.added.successfully", new String[]{genre.getName(), String.valueOf(genre.getId())}, cfg.getLocale());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ms.getMessage("genre.adding.error", null, cfg.getLocale());
    }

    @ShellMethod(key = {"add-book-comment", "abc"}, value = "Add comment for book in library: abc BookIdentifier \"comment\"")
    public String addBookComment(@ShellOption long bookId, @ShellOption String commentText) {
        try {
            CommentDto commentDto = commentService.addComment(bookId, commentText);
            return ms.getMessage("comment.added.successfully",
                    new String[]{commentDto.getBookTitle()}, cfg.getLocale());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ms.getMessage("comment.added.error", null, cfg.getLocale());
    }

    @ShellMethod(key = {"edit-book-comment", "ebc"}, value = "Edit comment for book in library: ebc CommentIdentifier \"comment\"")
    public String editBookComment(@ShellOption long commentId, @ShellOption String commentText) {
        try {
            CommentDto commentDto = commentService.updateComment(commentId, commentText);

            return ms.getMessage("comment.edited.successfully",
                    new String[]{commentDto.getBookTitle()}, cfg.getLocale());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ms.getMessage("comment.edited.error", null, cfg.getLocale());
    }

    @ShellMethod(key = {"remove-book-comment", "rbc"}, value = "Remove comment for book in library: rbc CommentIdentifier")
    public String removeBookComment(@ShellOption long commentId) {
        try {
            commentService.removeComment(commentId);
            return ms.getMessage("comment.removed.successfully", null, cfg.getLocale());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ms.getMessage("comment.removed.error", null, cfg.getLocale());
    }

    @ShellMethod(key = {"clear-book-comments", "сbc"}, value = "Clear all comments for book in library: cbc BookId")
    public String clearBookComments(@ShellOption long bookId) {
        try {
            commentService.clearComments(bookId);
            return ms.getMessage("comment.cleared.successfully", null, cfg.getLocale());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ms.getMessage("comment.cleared.error", null, cfg.getLocale());
    }

    @ShellMethod(key = {"display-book-comment", "dbc"}, value = "Display all comments for book in library: dbc BookId")
    public List<String> displayBookComments(@ShellOption long bookId) {
        try {
            ArrayList<String> result = new ArrayList<>();
            BookDto book = bookService.getBook(bookId);
            result.add(ms.getMessage("comment.display.title",
                    new String[]{book.getTitle()}, cfg.getLocale()));
            if (book.getComments().isEmpty()) {
                result.add(ms.getMessage("comment.display.no.comments",
                        new String[]{book.getTitle()}, cfg.getLocale()));
            } else {
                result.addAll(book.getComments());
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return List.of(ms.getMessage("comment.display.error", null, cfg.getLocale()));
    }
}
