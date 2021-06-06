package ru.otus.mylibrary.dto.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.dto.AuthorDto;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс AuthorDtoEntityConverterImplTest должен")
class AuthorDtoEntityConverterImplTest {

    private AuthorDtoEntityConverterImpl converter;
    private Author modelAuthor = Author.builder()
            .id(1)
            .name("Author")
            .build();

    private AuthorDto modelAuthorDto = AuthorDto.builder()
            .id(1)
            .name("Author")
            .build();

    @BeforeEach
    void setUp() {
        converter = new AuthorDtoEntityConverterImpl();
    }

    @Test
    @DisplayName("правильно преобразовать доменную модель в dto")
    void from() {
        AuthorDto authorDto = converter.from(modelAuthor);
        assertThat(authorDto).usingRecursiveComparison().isEqualTo(modelAuthorDto);
    }

    @Test
    @DisplayName("правильно преобразовать dto в доменную модель")
    void to() {
        Author author = converter.to(modelAuthorDto);
        assertThat(author).usingRecursiveComparison().isEqualTo(modelAuthor);
    }
}