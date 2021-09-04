package ru.otus.mylibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.dto.AuthorDto;
import ru.otus.mylibrary.dto.DtoConverter;
import ru.otus.mylibrary.exception.AuthorServiceAddAuthorException;
import ru.otus.mylibrary.repositories.AuthorRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final DtoConverter<AuthorDto, Author> dtoConverter;
    private final ExampleMatcher authorByNameMatcher =
            ExampleMatcher.matching()
                    .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                    .withIgnorePaths("id");

    @Override
    @Transactional
    public AuthorDto saveAuthor(AuthorDto authorDto) {
        Author author = dtoConverter.to(authorDto);
        if (author.getId() == 0) {
            Example<Author> authorExample = Example.of(author, authorByNameMatcher);
            return dtoConverter.from(authorRepository
                    .findOne(authorExample)
                    .orElse(authorRepository.save(author)));
        }
        return dtoConverter.from(authorRepository.save(author));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(dtoConverter::from)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public AuthorDto addAuthor(AuthorDto authorDto) {
        Author author = dtoConverter.to(authorDto);
        if (authorRepository.exists(Example.of(author, authorByNameMatcher))) {
            throw new AuthorServiceAddAuthorException(MessageFormat.format("The author \"{0}\" is already exists in library", authorDto.getName()));
        }
        return dtoConverter.from(authorRepository.save(author));
    }

    @Override
    public Optional<AuthorDto> findAuthorByName(String name) {
        return authorRepository
                .findByName(name)
                .map(dtoConverter::from);
    }
}
