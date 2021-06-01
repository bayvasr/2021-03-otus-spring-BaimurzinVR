package ru.otus.mylibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.mylibrary.dao.GenreDao;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.GenreDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс GenreServiceImpl должен")
class GenreServiceImplTest {
    private GenreService service;
    @Mock
    private GenreDao genreDao;

    @BeforeEach
    void init() {
        service = new GenreServiceImpl(genreDao);
    }

    @Test
    @DisplayName("добавлять жанр, если его нет в базе данных")
    void shouldAddGenre() {
        Genre genre = new Genre("Фантастика");
        Genre daoInsertReturnGenre = new Genre(1, "Фантастика");

        when(genreDao.find(genre)).thenReturn(Optional.empty());
        when(genreDao.insert(genre)).thenReturn(daoInsertReturnGenre);

        Genre savedGenre = service.saveGenre(genre);

        assertThat(savedGenre).usingRecursiveComparison().isEqualTo(daoInsertReturnGenre);
    }

    @Test
    @DisplayName("при сохранении вернуть найденный жанр, если уже есть в базе данных")
    void shouldSaveGetGenreIfFind() {
        Genre genre = new Genre("Фантастика");
        Genre daoFindReturnGenre = new Genre(1, "Фантастика");

        when(genreDao.find(genre)).thenReturn(Optional.of(daoFindReturnGenre));

        Genre savedGenre = service.saveGenre(genre);

        assertThat(savedGenre).usingRecursiveComparison().isEqualTo(daoFindReturnGenre);
    }

    @Test
    @DisplayName("получать все жанры")
    void shouldGetAllGenres() {
        service = new GenreServiceImpl(genreDao);
        Genre genre1 = new Genre(1, "Проза");
        Genre genre2 = new Genre(2, "Фантастика");
        List<Genre> list = List.of(genre1, genre2);

        when(genreDao.getAll()).thenReturn(list);

        List<String> actualList = service.getAllGenres();

        assertThat(actualList).containsAll(Stream.of(genre1, genre2).map(GenreDto::new).map(GenreDto::toString).collect(Collectors.toList()));
    }

}