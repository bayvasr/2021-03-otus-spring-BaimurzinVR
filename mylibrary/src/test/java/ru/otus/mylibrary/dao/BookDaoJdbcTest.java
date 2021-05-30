package ru.otus.mylibrary.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.BEFORE_METHOD;

@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
@DisplayName("Класс BookDaoJdbc должен")
class BookDaoJdbcTest {
    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("добавлять книгу в базу данных")
    void shouldInsertBook() {
        Book book = new Book("Стальная крыса", Author.UNKNOWN_AUTHOR, Genre.UNKNOWN_GENRE);
        Book insertedBook = bookDao.insert(book);
        assertThat(insertedBook.getId()).isPositive();
    }

    @Test
    @DisplayName("изменять книгу в базе данных")
    void shouldUpdateBook() {
        Book foundBook = bookDao.getById(3).orElse(Book.UNKNOWN_BOOK);

        Author author = new Author(2, "Тургенев И.С.");
        Genre genre = new Genre(2, "Роман");
        Book updBook = new Book(foundBook.getId(), foundBook.getTitle(), author, genre);

        bookDao.update(updBook);

        Book getByIdBook = bookDao.getById(updBook.getId()).orElse(Book.UNKNOWN_BOOK);
        assertThat(getByIdBook).usingRecursiveComparison().isEqualTo(updBook);
    }

    @Test
    @DisplayName("удалять книгу из базы данных")
    void shouldDeleteBookById() {
        Book foundBook = bookDao.getById(1).orElse(Book.UNKNOWN_BOOK);
        assertThat(foundBook.getId()).isPositive();

        bookDao.deleteById(foundBook.getId());

        Optional<Book> getByIdBookOptional = bookDao.getById(1);
        assertThat(getByIdBookOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("находить книгу в базе данных")
    void shouldFindBook() {
        Author author = new Author(1, "Айзек Азимов");
        Genre genre = new Genre(1, "Фантастика");
        Book book = new Book("Я, робот", author, genre);
        Book comparingBook = new Book(1, "Я, робот", author, genre);

        Book foundBook = bookDao.find(book).orElse(Book.UNKNOWN_BOOK);

        assertThat(foundBook).usingRecursiveComparison().isEqualTo(comparingBook);
    }

    @Test
    @DirtiesContext(methodMode = BEFORE_METHOD)
    @DisplayName("получать все книги из базы данных")
    void shouldGetAllBooks() {
        Author author = new Author(1, "Айзек Азимов");
        Genre genre = new Genre(1, "Фантастика");
        Book book1 = new Book(1, "Я, робот", author, genre);
        Book book2 = new Book(2, "Пикник на обочине", Author.UNKNOWN_AUTHOR, genre);
        Book book3 = new Book(3, "Отцы и дети", Author.UNKNOWN_AUTHOR, Genre.UNKNOWN_GENRE);
        List<Book> bookList = List.of(book1, book2, book3);
        List<Book> getAllBookList = bookDao.getAll();

        assertThat(getAllBookList.size()).isEqualTo(3);
        assertThat(getAllBookList).usingFieldByFieldElementComparator().isEqualTo(bookList);
    }
}