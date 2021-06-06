package ru.otus.mylibrary.service;

import ru.otus.mylibrary.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorDto saveAuthor(AuthorDto author);

    List<String> getAllAuthors();
}
