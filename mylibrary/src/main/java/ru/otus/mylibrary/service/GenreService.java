package ru.otus.mylibrary.service;

import ru.otus.mylibrary.domain.Genre;

import java.util.List;

public interface GenreService {
    Genre saveGenre(Genre genre);

    List<String> getAllGenres();


}
