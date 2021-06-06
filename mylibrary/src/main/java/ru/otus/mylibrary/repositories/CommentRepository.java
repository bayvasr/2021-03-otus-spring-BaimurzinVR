package ru.otus.mylibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mylibrary.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookId(Long bookId);
}