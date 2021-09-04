package ru.otus.mylibrary.service;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.mylibrary.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    GenreDto saveGenre(GenreDto genre);

    List<GenreDto> getAllGenres();


    GenreDto addGenre(GenreDto genreDto);

    @Transactional(readOnly = true)
    Optional<GenreDto> findGenreByName(String name);
}
