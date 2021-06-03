package ru.otus.mylibrary.dto;

import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.domain.Book;

public class AuthorDto {
    private final long id;
    private final String name;

    public AuthorDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
    }

    @Override
    public String toString() {
        return String.format("%d. %s", id, name);
    }
}
