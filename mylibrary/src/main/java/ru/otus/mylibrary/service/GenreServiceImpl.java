package ru.otus.mylibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.DtoConverter;
import ru.otus.mylibrary.dto.GenreDto;
import ru.otus.mylibrary.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final DtoConverter<GenreDto, Genre> dtoConverter;

    @Override
    @Transactional
    public GenreDto saveGenre(GenreDto genre) {
        return dtoConverter.from(genreRepository.save(dtoConverter.to(genre)));
    }


    @Override
    @Transactional(readOnly = true)
    public List<String> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(dtoConverter::from)
                .map(GenreDto::toString)
                .collect(Collectors.toList());
    }
}
