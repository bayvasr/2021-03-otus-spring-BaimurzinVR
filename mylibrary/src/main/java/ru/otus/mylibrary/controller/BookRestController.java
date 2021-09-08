package ru.otus.mylibrary.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.mylibrary.dto.BookDto;
import ru.otus.mylibrary.dto.CommentDto;
import ru.otus.mylibrary.exception.*;
import ru.otus.mylibrary.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mylibrary/books")
public class BookRestController {
    private final BookService bookService;

    private static final Logger logger = LoggerFactory.getLogger(BookRestController.class);

    @GetMapping()
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("{bookId}")
    public ResponseEntity<BookDto> getBook(@PathVariable("bookId") long bookId) {
        BookDto book = bookService.getBook(bookId);
        return ResponseEntity.ok(book);
    }

    @PostMapping()
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        BookDto addedBook = bookService.addBook(bookDto);
        return ResponseEntity.ok(addedBook);
    }

    @PutMapping()
    public ResponseEntity<BookDto> editBook(@RequestBody BookDto bookDto) {
        BookDto updateBook = bookService.updateBook(bookDto);
        return ResponseEntity.ok(updateBook);

    }

    @DeleteMapping("{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable("bookId") long bookId) {
        bookService.removeBook(bookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{bookId}/comments")
    public ResponseEntity<List<CommentDto>> getBookComments(@PathVariable("bookId") long bookId) {
        List<CommentDto> commentList = bookService.getBookComments(bookId);
        return ResponseEntity.ok(commentList);
    }

    @PostMapping("{bookId}/comments")
    public ResponseEntity<List<CommentDto>> addBookComment(@PathVariable("bookId") long bookId, @RequestBody String comment) {
        List<CommentDto> commentList = bookService.addBookComment(bookId, comment);
        return ResponseEntity.ok(commentList);
    }

    @DeleteMapping("{bookId}/comments/{commentId}")
    public ResponseEntity<List<CommentDto>> deleteBookComment(@PathVariable("bookId") long bookId, @PathVariable("commentId") long commentId) {
        List<CommentDto> commentList = bookService.removeBookComment(bookId, commentId);
        return ResponseEntity.ok(commentList);
    }

    @ExceptionHandler(BookServiceAddBookException.class)
    public ResponseEntity<String> handleBookServiceAddBookException(BookServiceAddBookException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(BookServiceFindBookException.class)
    public ResponseEntity<String> handleBookServiceFindBookException(BookServiceFindBookException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(BookServiceRemoveBookException.class)
    public ResponseEntity<String> handleBookServiceRemoveBookException(BookServiceRemoveBookException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(BookServiceUpdateBookException.class)
    public ResponseEntity<String> handleBookServiceUpdateBookException(BookServiceUpdateBookException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CommentServiceNotFoundException.class)
    public ResponseEntity<String> handleCommentServiceNotFoundException(CommentServiceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
