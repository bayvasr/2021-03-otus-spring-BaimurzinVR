package ru.otus.mylibrary.dto.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.BookDto;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс BookDtoEntityConverterImplTest должен")
class BookDtoEntityConverterImplTest {
    private BookDtoEntityConverterImpl converter;
    private Book modelBook = Book.builder()
            .id(1)
            .title("Title")
            .author(Author.builder().id(0).name("неизвестен").build())
            .genre(Genre.builder().id(0).name("неизвестен").build())
            .comments(new ArrayList<>())
            .build();

    private BookDto modelBookDto = BookDto.builder()
            .id(1)
            .title("Title")
            .genreId(0)
            .genre("неизвестен")
            .authorId(0)
            .author("неизвестен")
            .build();

    @BeforeEach
    void setUp() {
        converter = new BookDtoEntityConverterImpl();
    }

    @Test
    @DisplayName("правильно преобразовать доменную модель в dto")
    void from() {
        BookDto bookDto = converter.from(modelBook);
        assertThat(bookDto).usingRecursiveComparison().isEqualTo(modelBookDto);
    }

    @Test
    @DisplayName("правильно преобразовать dto в доменную модель")
    void to() {
        Book book = converter.to(modelBookDto);
        assertThat(book).usingRecursiveComparison().isEqualTo(modelBook);
    }
}