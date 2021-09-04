package ru.otus.mylibrary.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    private final long id;
    private final String text;
    private final String bookTitle;
    private final long bookId;


    @Override
    public String toString() {
        return "Комментарий " +
                id +
                ". " + text;
    }
}
