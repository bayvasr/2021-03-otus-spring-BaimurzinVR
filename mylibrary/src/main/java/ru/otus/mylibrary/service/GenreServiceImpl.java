package ru.otus.mylibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mylibrary.dao.GenreDao;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.GenreDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao dao;

    @Override
    public Genre saveGenre(Genre genre) {
        return dao.find(genre)
                .orElseGet(() -> dao.insert(genre));
    }


    @Override
    public List<String> getAllGenres() {
        return dao.getAll().stream()
                .map(GenreDto::new)
                .map(GenreDto::toString)
                .collect(Collectors.toList());
    }
}
