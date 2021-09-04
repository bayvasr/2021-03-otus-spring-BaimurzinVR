package ru.otus.mylibrary.service;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.mylibrary.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorDto saveAuthor(AuthorDto author);

    List<AuthorDto> getAllAuthors();

    @Transactional
    AuthorDto addAuthor(AuthorDto authorDto);

    Optional<AuthorDto> findAuthorByName(String name);
}
