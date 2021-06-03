package ru.otus.mylibrary.domain;

import lombok.Data;

@Data
public class Book {

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

    public long getAuthorId() {
        return author == null ? 0 : author.getId();
    }

    public long getGenreId() {
        return genre == null ? 0 : genre.getId();
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                '}';
    }
}
