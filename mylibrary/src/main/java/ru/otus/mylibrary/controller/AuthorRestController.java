package ru.otus.mylibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.mylibrary.dto.AuthorDto;
import ru.otus.mylibrary.exception.AuthorServiceAddAuthorException;
import ru.otus.mylibrary.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mylibrary/authors")
public class AuthorRestController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @PostMapping
    public ResponseEntity<AuthorDto> addAuthor(@RequestBody String author) {

        return ResponseEntity.ok(authorService.addAuthor(AuthorDto.builder().id(0).name(author).build()));
    }

    @ExceptionHandler(AuthorServiceAddAuthorException.class)
    public ResponseEntity<String> handleAuthorServiceAddAuthorException(AuthorServiceAddAuthorException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
