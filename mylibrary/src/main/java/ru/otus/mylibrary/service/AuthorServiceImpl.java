package ru.otus.mylibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.dto.AuthorDto;
import ru.otus.mylibrary.dto.DtoConverter;
import ru.otus.mylibrary.repositories.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final DtoConverter<AuthorDto, Author> dtoConverter;

    @Override
    @Transactional
    public AuthorDto saveAuthor(AuthorDto author) {
        return dtoConverter.from(authorRepository.save(dtoConverter.to(author)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(dtoConverter::from)
                .map(AuthorDto::toString)
                .collect(Collectors.toList());
    }
}
