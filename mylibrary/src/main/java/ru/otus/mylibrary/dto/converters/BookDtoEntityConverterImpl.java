package ru.otus.mylibrary.dto.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.AuthorDto;
import ru.otus.mylibrary.dto.BookDto;
import ru.otus.mylibrary.dto.DtoConverter;
import ru.otus.mylibrary.dto.GenreDto;
import ru.otus.mylibrary.service.AuthorService;
import ru.otus.mylibrary.service.GenreService;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class BookDtoEntityConverterImpl implements DtoConverter<BookDto, Book> {

    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public BookDto from(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor() == null ? "неизвестен" : book.getAuthor().getName())
                .genre(book.getGenre() == null ? "неизвестен" : book.getGenre().getName())
                .build();
    }

    @Override
    public Book to(BookDto bookDto) {
        Book book = Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .comments(new ArrayList<>())
                .build();
        if (StringUtils.hasText(bookDto.getAuthor())) {
            AuthorDto authorDto = authorService.findAuthorByName(bookDto.getAuthor())
                    .orElse(AuthorDto.builder().id(0).name(bookDto.getAuthor()).build());
            book.setAuthor(Author.builder().id(authorDto.getId()).name(authorDto.getName()).build());
        }

        if (StringUtils.hasText(bookDto.getGenre())) {
            GenreDto genreDto = genreService.findGenreByName(bookDto.getGenre())
                    .orElse(GenreDto.builder().id(0).name(bookDto.getGenre()).build());
            book.setGenre(Genre.builder().id(genreDto.getId()).name(genreDto.getName()).build());
        }

        return book;
    }


}
