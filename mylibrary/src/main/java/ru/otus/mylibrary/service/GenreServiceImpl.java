package ru.otus.mylibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.DtoConverter;
import ru.otus.mylibrary.dto.GenreDto;
import ru.otus.mylibrary.exception.GenreServiceAddGenreException;
import ru.otus.mylibrary.repositories.GenreRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final DtoConverter<GenreDto, Genre> dtoConverter;
    private final ExampleMatcher genreByNameMatcher =
            ExampleMatcher.matching()
                    .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                    .withIgnorePaths("id");

    @Override
    @Transactional
    public GenreDto saveGenre(GenreDto genreDto) {
        Genre genre = dtoConverter.to(genreDto);
        if (genre.getId() == 0) {
            Example<Genre> genreExample = Example.of(genre, genreByNameMatcher);
            return dtoConverter.from(genreRepository
                    .findOne(genreExample)
                    .orElse(genreRepository.save(genre)));
        }
        return dtoConverter.from(genreRepository.save(genre));
    }


    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(dtoConverter::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GenreDto addGenre(GenreDto genreDto) {
        Genre genre = dtoConverter.to(genreDto);
        if (genreRepository.exists(Example.of(genre, genreByNameMatcher))) {
            throw new GenreServiceAddGenreException(MessageFormat.format("The genre \"{0}\" is already exists in library", genreDto.getName()));
        }

        return dtoConverter.from(genreRepository.save(genre));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GenreDto> findGenreByName(String name) {
        return genreRepository.findByName(name)
                .map(dtoConverter::from);
    }
}
