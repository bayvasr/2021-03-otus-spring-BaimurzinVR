package ru.otus.mylibrary.domain;

import lombok.Data;

@Data
public class Genre {

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
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
