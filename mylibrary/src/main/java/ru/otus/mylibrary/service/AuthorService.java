package ru.otus.mylibrary.service;

import ru.otus.mylibrary.domain.Author;

import java.util.List;

public interface AuthorService {
    Author saveAuthor(Author author);

    List<String> getAllAuthors();
}
