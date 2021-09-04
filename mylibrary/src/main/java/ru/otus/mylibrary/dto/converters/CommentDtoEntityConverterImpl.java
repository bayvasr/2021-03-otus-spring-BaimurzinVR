package ru.otus.mylibrary.dto.converters;

import org.springframework.stereotype.Component;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Comment;
import ru.otus.mylibrary.dto.CommentDto;
import ru.otus.mylibrary.dto.DtoConverter;

@Component
public class CommentDtoEntityConverterImpl implements DtoConverter<CommentDto, Comment> {
    @Override
    public CommentDto from(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .bookTitle(comment.getBook().getTitle())
                .bookId(comment.getBook().getId())
                .text(comment.getText())
                .build();
    }

    @Override
    public Comment to(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .book(Book.builder().id(commentDto.getBookId()).build())
                .text(commentDto.getText())
                .build();
    }

}
