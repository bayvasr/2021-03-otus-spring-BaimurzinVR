package ru.otus.mylibrary.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.mylibrary.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.BEFORE_METHOD;

@JdbcTest
@Import(AuthorDaoJdbc.class)
@DisplayName("Класс AuthorDaoJdbc должен")
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("добавлять автора в базу данных")
    void shouldInsertAuthor() {
        Author author = new Author("Маяковский В.В.");
        Author savedAuthor = authorDao.insert(author);
        assertThat(savedAuthor.getId()).isPositive();
        assertThat(savedAuthor.getName()).isEqualTo(author.getName());
    }

    @Test
    @DisplayName("находить автора в базе данных")
    void shouldFindAuthor() {
        Author author = new Author("Маяковский В.В.");
        authorDao.insert(author);
        Optional<Author> optionalAuthor = authorDao.find(author);
        assertThat(optionalAuthor).isPresent();
        assertThat(optionalAuthor.get().getId()).isPositive();
        assertThat(optionalAuthor.get().getName()).isEqualTo(author.getName());
    }

    @Test
    @DirtiesContext(methodMode = BEFORE_METHOD)
    @DisplayName("получать всех авторов из базы данных")
    void shouldGetAllAuthors() {
        Author author = new Author("Маяковский В.В.");
        Author insertedAuthor1 = authorDao.insert(author);
        Author author2 = new Author("Гарри Гаррисон");
        Author insertedAuthor2 = authorDao.insert(author2);
        List<Author> authors = authorDao.getAll();
        assertThat(authors.size()).isEqualTo(4);
        assertThat(authors).containsAll(List.of(insertedAuthor1, insertedAuthor2));
    }

    @Test
    @DisplayName("находить автора в базе данных по идентификатору")
    void shouldGetAuthorById() {
        Author expAuthor = new Author("Маяковский В.В.");
        Author insAuthor = authorDao.insert(expAuthor);
        Optional<Author> authorById = authorDao.getById(insAuthor.getId());
        assertThat(authorById).isPresent();
        assertThat(authorById.get().getName()).isEqualTo(expAuthor.getName());
    }
}