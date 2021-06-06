package ru.otus.mylibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Comment;
import ru.otus.mylibrary.dto.CommentDto;
import ru.otus.mylibrary.dto.DtoConverter;
import ru.otus.mylibrary.dto.converters.CommentDtoEntityConverterImpl;
import ru.otus.mylibrary.repositories.BookRepository;
import ru.otus.mylibrary.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс CommentServiceImplTest должен")
class CommentServiceImplTest {

    private CommentServiceImpl service;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookRepository bookRepository;
    private DtoConverter<CommentDto, Comment> dtoConverter = new CommentDtoEntityConverterImpl();


    @BeforeEach
    void setUp() {
        service = new CommentServiceImpl(commentRepository, bookRepository, dtoConverter);
    }

    @Test
    @DisplayName("должен корректно добавлять комментарий к существующей книге")
    void shouldAddComment() {
        Book book = Book.builder().id(1).build();
        Comment comment = Comment.builder().id(1).text("New comment").book(book).build();
        CommentDto savedComment = CommentDto.builder().bookId(1).id(1).text("New comment").build();
        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(book));
        when(commentRepository.save(any())).thenReturn(comment);
        CommentDto addingComment = service.addComment(1, "New comment");
        assertThat(addingComment).usingRecursiveComparison().isEqualTo(savedComment);

    }

    @Test
    @DisplayName("корректно обновлять существующий комментарий")
    void updateComment() {
        Book book = Book.builder().id(1).build();
        Comment comment = Comment.builder().id(1).text("New comment").book(book).build();
        Comment updatedComment = Comment.builder().id(1).text("Old comment").book(book).build();
        CommentDto existsComment = CommentDto.builder().bookId(1).id(1).text("New comment").build();
        when(commentRepository.findById(1L)).thenReturn(Optional.ofNullable(comment));
        when(commentRepository.save(any())).thenReturn(updatedComment);
        CommentDto updatedCommentDto = service.updateComment(1, "Old comment");
        assertThat(updatedCommentDto).isNotNull();
        assertThat(updatedCommentDto.getId()).isEqualTo(existsComment.getId());
        assertThat(updatedCommentDto.getText()).isNotEqualTo(existsComment.getText());
    }

    @Test
    @DisplayName("удалять комментарий по его идентификатору")
    void removeComment() {
        Book book = Book.builder().id(1).build();
        Comment comment = Comment.builder().id(1).text("New comment").book(book).build();
        Comment comment2 = Comment.builder().id(1).text("Old comment").book(book).build();

        List<Comment> comments = new ArrayList<>(List.of(comment, comment2));

        doAnswer((i) -> comments.remove(0)).when(commentRepository).deleteById(1L);


        service.removeComment(1);
        assertThat(comments).doesNotContain(comment);
    }

    @Test
    @DisplayName("очищать все комментарии по книге")
    void clearComments() {
        Book book = Book.builder().id(1).build();
        Comment comment = Comment.builder().id(1).text("New comment").book(book).build();
        Comment comment2 = Comment.builder().id(1).text("Old comment").book(book).build();

        List<Comment> comments = new ArrayList<>(List.of(comment, comment2));

        when(commentRepository.findByBookId(1L)).thenReturn(comments);

        doAnswer((i) -> {
            comments.clear();
            return null;
        }).when(commentRepository).deleteAll(any());

        service.clearComments(1);

        assertThat(comments).isEmpty();
    }

    @Test
    @DisplayName("получать все комментарии по книге")
    void getComments() {
        Book book = Book.builder().id(1).build();
        Comment comment = Comment.builder().id(1).text("New comment").book(book).build();
        Comment comment2 = Comment.builder().id(1).text("Old comment").book(book).build();

        List<Comment> comments = new ArrayList<>(List.of(comment, comment2));

        when(commentRepository.findByBookId(1L)).thenReturn(comments);

        CommentDto commentDto = CommentDto.builder().id(1).text("New comment").bookId(1).build();
        CommentDto commentDto2 = CommentDto.builder().id(1).text("Old comment").bookId(1).build();
        List<CommentDto> commentDtos = new ArrayList<>(List.of(commentDto, commentDto2));

        List<CommentDto> getComments = service.getComments(1);

        assertThat(getComments).containsAll(commentDtos);
    }
}