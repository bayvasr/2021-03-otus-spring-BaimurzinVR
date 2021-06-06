package ru.otus.mylibrary.dto.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Comment;
import ru.otus.mylibrary.dto.CommentDto;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс AuthorDtoEntityConverterImplTest должен")
class CommentDtoEntityConverterImplTest {
    private CommentDtoEntityConverterImpl converter;
    private Comment modelComment = Comment.builder()
            .id(1)
            .book(Book.builder().id(1).build())
            .text("Author")
            .build();

    private CommentDto modelCommentDto = CommentDto.builder()
            .id(1)
            .bookId(1)
            .text("Author")
            .build();

    @BeforeEach
    void setUp() {
        converter = new CommentDtoEntityConverterImpl();
    }

    @Test
    @DisplayName("правильно преобразовать доменную модель в dto")
    void from() {
        CommentDto commentDto = converter.from(modelComment);
        assertThat(commentDto).usingRecursiveComparison().isEqualTo(modelCommentDto);
    }

    @Test
    @DisplayName("правильно преобразовать dto в доменную модель")
    void to() {
        Comment comment = converter.to(modelCommentDto);
        assertThat(comment).usingRecursiveComparison().isEqualTo(modelComment);
    }
}