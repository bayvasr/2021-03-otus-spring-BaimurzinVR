package ru.otus.mylibrary.dto.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.AuthorDto;
import ru.otus.mylibrary.dto.BookDto;
import ru.otus.mylibrary.dto.GenreDto;
import ru.otus.mylibrary.service.AuthorService;
import ru.otus.mylibrary.service.GenreService;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Класс BookDtoEntityConverterImplTest должен")
@ExtendWith(MockitoExtension.class)
class BookDtoEntityConverterImplTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private GenreService genreService;

    private BookDtoEntityConverterImpl converter;
    private Book modelBook = Book.builder()
            .id(1)
            .title("Title")
            .author(Author.builder().id(0).name("неизвестен").build())
            .genre(Genre.builder().id(0).name("неизвестен").build())
            .comments(new ArrayList<>())
            .build();

    private Book modelBook2 = Book.builder()
            .id(1)
            .title("Title")
            .author(Author.builder().id(100).name("неизвестен").build())
            .genre(Genre.builder().id(200).name("неизвестен").build())
            .comments(new ArrayList<>())
            .build();

    private BookDto modelBookDto = BookDto.builder()
            .id(1)
            .title("Title")
            .genre("неизвестен")
            .author("неизвестен")
            .build();

    @BeforeEach
    void setUp() {
        converter = new BookDtoEntityConverterImpl(authorService, genreService);
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
        when(authorService.findAuthorByName(modelBookDto.getAuthor())).thenReturn(Optional.empty());
        when(genreService.findGenreByName(modelBookDto.getGenre())).thenReturn(Optional.empty());
        Book book = converter.to(modelBookDto);
        assertThat(book).usingRecursiveComparison().isEqualTo(modelBook);

        when(authorService.findAuthorByName(modelBookDto.getAuthor())).thenReturn(
                Optional.ofNullable(AuthorDto.builder().id(100).name(modelBookDto.getAuthor()).build())
        );
        when(genreService.findGenreByName(modelBookDto.getGenre())).thenReturn(
                Optional.ofNullable(GenreDto.builder().id(200).name(modelBookDto.getGenre()).build())
        );
        book = converter.to(modelBookDto);
        assertThat(book).usingRecursiveComparison().isEqualTo(modelBook2);

    }


}