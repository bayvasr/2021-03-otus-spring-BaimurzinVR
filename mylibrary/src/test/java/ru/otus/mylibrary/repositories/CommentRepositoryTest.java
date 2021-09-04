package ru.otus.mylibrary.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.mylibrary.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями должен")
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("должен вернуть все комментарии по книге по его идентификатору")
    void findByBookId() {
        List<Comment> commentsByBookId = repository.findByBookId(1L);
        List<Comment> comments = List.of(em.find(Comment.class, 1L), em.find(Comment.class, 2L));
        assertThat(commentsByBookId).usingFieldByFieldElementComparator().isEqualTo(comments);

    }
}