package ru.otus.mylibrary.dto;

import ru.otus.mylibrary.domain.Book;

public class BookDto {
    private final long id;
    private final String title;
    private final String author;
    private final String genre;

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor().toString();
        this.genre = book.getGenre().toString();
    }

    @Override
    public String toString() {
        return String.format("%d. %s, %s, %s", id, title, author, genre);
    }
}
