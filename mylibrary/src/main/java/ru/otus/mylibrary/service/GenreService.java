package ru.otus.mylibrary.service;

import ru.otus.mylibrary.dto.GenreDto;

import java.util.List;

public interface GenreService {
    GenreDto saveGenre(GenreDto genre);

    List<String> getAllGenres();


}
