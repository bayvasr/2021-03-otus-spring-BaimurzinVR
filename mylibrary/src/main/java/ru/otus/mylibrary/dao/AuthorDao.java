package ru.otus.mylibrary.dao;

import ru.otus.mylibrary.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Author insert(Author author);

    Optional<Author> find(Author author);

    List<Author> getAll();

    Optional<Author> getById(long id);
}
