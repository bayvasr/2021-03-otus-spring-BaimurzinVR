package ru.otus.mylibrary.domain;

import lombok.Data;

@Data
public class Genre {

    public static final Genre UNKNOWN_GENRE = new Genre("Unknown");

    private final long id;
    private final String name;

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String name) {
        this(0, name);
    }

    @Override
    public String toString() {
        return "Жанр: " +
                name + " (Идентификатор " +
                id + ")";
    }
}
