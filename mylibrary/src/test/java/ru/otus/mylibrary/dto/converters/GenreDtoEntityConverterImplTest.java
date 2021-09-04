package ru.otus.mylibrary.dto.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.GenreDto;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс GenreDtoEntityConverterImplTest должен")
class GenreDtoEntityConverterImplTest {

    private GenreDtoEntityConverterImpl converter;
    private Genre modelGenre = Genre.builder()
            .id(1)
            .name("Genre")
            .build();

    private GenreDto modelGenreDto = GenreDto.builder()
            .id(1)
            .name("Genre")
            .build();

    @BeforeEach
    void setUp() {
        converter = new GenreDtoEntityConverterImpl();
    }

    @Test
    @DisplayName("правильно преобразовать доменную модель в dto")
    void from() {
        GenreDto genreDto = converter.from(modelGenre);
        assertThat(genreDto).usingRecursiveComparison().isEqualTo(modelGenreDto);
    }

    @Test
    @DisplayName("правильно преобразовать dto в доменную модель")
    void to() {
        Genre genre = converter.to(modelGenreDto);
        assertThat(genre).usingRecursiveComparison().isEqualTo(modelGenre);
    }
}