package ru.otus.mylibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.DtoConverter;
import ru.otus.mylibrary.dto.GenreDto;
import ru.otus.mylibrary.dto.converters.GenreDtoEntityConverterImpl;
import ru.otus.mylibrary.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс GenreServiceImpl должен")
class GenreServiceImplTest {
    private GenreService service;
    @Mock
    private GenreRepository genreRepository;

    private DtoConverter<GenreDto, Genre> dtoConverter = new GenreDtoEntityConverterImpl();


    @BeforeEach
    void init() {
        service = new GenreServiceImpl(genreRepository, dtoConverter);
    }

    @Test
    @DisplayName("добавлять жанр, если его нет в базе данных")
    void shouldAddGenre() {
        GenreDto genre = GenreDto.builder().name("Фантастика").build();
        Genre daoInsertReturnGenre = new Genre(1, "Фантастика");

        when(genreRepository.save(any())).thenReturn(daoInsertReturnGenre);

        GenreDto savedGenre = service.saveGenre(genre);

        assertThat(savedGenre).usingRecursiveComparison().isEqualTo(daoInsertReturnGenre);
    }

//    @Test
//    @DisplayName("при сохранении вернуть найденный жанр, если уже есть в базе данных")
//    void shouldSaveGetGenreIfFind() {
//        Genre genre = new Genre("Фантастика");
//        Genre daoFindReturnGenre = new Genre(1, "Фантастика");
//
//        when(genreRepository.find(genre)).thenReturn(Optional.of(daoFindReturnGenre));
//
//        Genre savedGenre = service.saveGenre(genre);
//
//        assertThat(savedGenre).usingRecursiveComparison().isEqualTo(daoFindReturnGenre);
//    }

    @Test
    @DisplayName("получать все жанры")
    void shouldGetAllGenres() {
        Genre genre1 = new Genre(1, "Проза");
        Genre genre2 = new Genre(2, "Фантастика");
        List<Genre> list = List.of(genre1, genre2);

        when(genreRepository.findAll()).thenReturn(list);

        List<GenreDto> actualList = service.getAllGenres();

        assertThat(actualList).containsAll(Stream.of(genre1, genre2).map(dtoConverter::from).collect(Collectors.toList()));
    }

}