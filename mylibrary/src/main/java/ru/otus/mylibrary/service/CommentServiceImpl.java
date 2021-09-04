package ru.otus.mylibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Comment;
import ru.otus.mylibrary.dto.CommentDto;
import ru.otus.mylibrary.dto.DtoConverter;
import ru.otus.mylibrary.exception.CommentServiceAddCommentException;
import ru.otus.mylibrary.exception.CommentServiceUpdateCommentException;
import ru.otus.mylibrary.repositories.BookRepository;
import ru.otus.mylibrary.repositories.CommentRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final DtoConverter<CommentDto, Comment> dtoConverter;

    @Override
    @Transactional
    public CommentDto addComment(long bookId, String text) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new CommentServiceAddCommentException(
                        MessageFormat.format("The book with ID {0} was not found in library", bookId)
                )
        );
        Comment savedComment = commentRepository.save(Comment.builder()
                .text(text)
                .book(book)
                .build());
        return dtoConverter.from(savedComment);
    }

    @Override
    @Transactional
    public CommentDto updateComment(long commentId, String text) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new CommentServiceUpdateCommentException(MessageFormat.format("The comment with ID {0} was not found in library", commentId)));
        comment.setText(text);
        return dtoConverter.from(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void removeComment(long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void clearComments(long bookId) {
        commentRepository.deleteAll(commentRepository.findByBookId(bookId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getComments(long bookId) {
        return commentRepository.findByBookId(bookId)
                .stream().map(dtoConverter::from).collect(Collectors.toList());
    }
}
