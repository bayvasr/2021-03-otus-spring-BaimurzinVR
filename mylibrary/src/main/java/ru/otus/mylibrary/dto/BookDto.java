package ru.otus.mylibrary.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Data
@Builder
public class BookDto {
    private final long id;
    private final String title;
    private final long authorId;
    private final String author;
    private final long genreId;
    private final String genre;
    @Builder.Default
    private List<String> comments = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("%d. %s, Автор: %s, Жанр: %s, Комментарии: %d",
                id, title, author, genre, comments.size());
    }
}
