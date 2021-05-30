package ru.otus.mylibrary.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Author {

    public static final Author UNKNOWN_AUTHOR = new Author("Unknown");

    private final long id;
    private final String name;

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author(String name) {
        this(0, name);
    }

    @Override
    public String toString() {
        return "Автор: " +
                name + " (Идентификатор " +
                id + ")";
    }
}
