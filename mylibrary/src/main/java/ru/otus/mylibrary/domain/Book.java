package ru.otus.mylibrary.domain;

import lombok.Data;

@Data
public class Book {
    public static final Book UNKNOWN_BOOK = new Book(0, "Unknown", Author.UNKNOWN_AUTHOR, Genre.UNKNOWN_GENRE);

    private final long id;
    private final String title;
    private final Author author;
    private final Genre genre;

    public Book(long id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String title, Author author, Genre genre) {
        this(0, title, author, genre);
    }


    @Override
    public String toString() {
        return "Книга (" +
                "id=" + id +
                ") Название='" + title + '\'' +
                " Автор=" + author +
                " Жанр=" + genre;
    }
}
