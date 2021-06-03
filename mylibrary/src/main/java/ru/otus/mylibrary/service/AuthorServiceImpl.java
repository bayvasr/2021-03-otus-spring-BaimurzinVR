package ru.otus.mylibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mylibrary.dao.AuthorDao;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.dto.AuthorDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao dao;

    @Override
    public Author saveAuthor(Author author) {
        return dao.find(author)
                .orElseGet(() -> dao.insert(author));
    }

    @Override
    public List<String> getAllAuthors() {
        return dao.getAll().stream().map(AuthorDto::new).map(AuthorDto::toString).collect(Collectors.toList());
    }
}
