package ru.otus.mylibrary.dto.converters;

import org.springframework.stereotype.Component;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.BookDto;
import ru.otus.mylibrary.dto.DtoConverter;

import java.util.ArrayList;

@Component
public class BookDtoEntityConverterImpl implements DtoConverter<BookDto, Book> {
    @Override
    public BookDto from(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorId(book.getAuthor() == null ? 0 : book.getAuthor().getId())
                .author(book.getAuthor() == null ? "неизвестен" : book.getAuthor().getName())
                .genreId(book.getGenre() == null ? 0 : book.getGenre().getId())
                .genre(book.getGenre() == null ? "неизвестен" : book.getGenre().getName())
                .build();
    }

    @Override
    public Book to(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .author(Author.builder().id(bookDto.getAuthorId()).name(bookDto.getAuthor()).build())
                .genre(Genre.builder().id(bookDto.getGenreId()).name(bookDto.getGenre()).build())
                .comments(new ArrayList<>())
                .build();
    }


}
