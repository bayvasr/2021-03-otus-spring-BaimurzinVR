package ru.otus.mylibrary.dto;

import ru.otus.mylibrary.domain.Genre;

public class GenreDto {
    private final long id;
    private final String name;

    public GenreDto(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }

    @Override
    public String toString() {
        return String.format("%d. %s", id, name);
    }
}
