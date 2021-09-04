package ru.otus.mylibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.mylibrary.dto.GenreDto;
import ru.otus.mylibrary.exception.GenreServiceAddGenreException;
import ru.otus.mylibrary.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mylibrary/genres")
public class GenreRestController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<GenreDto>> getGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @PostMapping
    public ResponseEntity<GenreDto> addGenre(@RequestBody String genre) {
        return ResponseEntity.ok(genreService.addGenre(GenreDto.builder().id(0).name(genre).build()));
    }

    @ExceptionHandler(GenreServiceAddGenreException.class)
    public ResponseEntity<String> handleGenreServiceAddGenreException(GenreServiceAddGenreException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
