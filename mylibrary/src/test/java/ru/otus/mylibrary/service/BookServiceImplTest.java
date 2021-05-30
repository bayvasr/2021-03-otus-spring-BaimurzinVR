package ru.otus.mylibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.mylibrary.dao.BookDao;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.BookDto;
import ru.otus.mylibrary.exception.BookServiceAddBookException;
import ru.otus.mylibrary.exception.BookServiceRemoveBookException;
import ru.otus.mylibrary.exception.BookServiceSaveBookException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс BookServiceImpl должен")
class BookServiceImplTest {
    private BookService service;
    @Mock
    private BookDao bookDao;

    @BeforeEach
    void init() {
        service = new BookServiceImpl(bookDao);
    }

    @Test
    @DisplayName("получать все книги")
    void shouldGetAllBooks() {
        Author author1 = new Author(1, "Маяковский В.В.");
        Author author2 = new Author(2, "Айзек Азимов");
        Genre genre1 = new Genre(1, "Проза");
        Genre genre2 = new Genre(1, "Фантастика");
        Book book1 = new Book(1, "Неоконченное", author1, genre1);
        Book book2 = new Book(2, "Я, робот", author2, genre2);
        List<Book> list = List.of(book1, book2);
        BookDto dto1 = new BookDto(book1);
        BookDto dto2 = new BookDto(book2);

        when(bookDao.getAll()).thenReturn(list);

        List<BookDto> actList = service.getAllBooks();

        assertThat(actList.get(0)).usingRecursiveComparison().isEqualTo(dto1);
        assertThat(actList.get(1)).usingRecursiveComparison().isEqualTo(dto2);
    }

    @Test
    @DisplayName("добавлять новую книгу")
    void shouldAddBook() {
        Author author = new Author(1, "Маяковский В.В.");
        Genre genre = new Genre(1, "Проза");
        Book book = new Book(1, "Неоконченное", author, genre);
        BookDto daoInsertedBookDto = new BookDto(book);

        when(bookDao.find(book)).thenReturn(Optional.empty());
        when(bookDao.insert(book)).thenReturn(book);

        BookDto addedDto = service.addBook(book);

        assertThat(addedDto).usingRecursiveComparison().isEqualTo(daoInsertedBookDto);
    }

    @Test
    @DisplayName("выдать ошибку, если добавляемая книга уже есть в базе данных")
    void shouldThrowExceptionIfBookToAddNotFoundById() {
        Author author = new Author(1, "Маяковский В.В.");
        Genre genre = new Genre(1, "Проза");
        Book book = new Book(1, "Неоконченное", author, genre);

        when(bookDao.find(any())).thenReturn(Optional.of(book));

        assertThatExceptionOfType(BookServiceAddBookException.class).isThrownBy(() -> service.addBook(book));
    }

    @Test
    @DisplayName("сохранять отредактированную книгу")
    void shouldSaveBook() {
        Author author = new Author(1, "Маяковский В.В.");
        Genre genre = new Genre(1, "Проза");
        Book book = new Book(1, "Неоконченное", author, genre);
        BookDto actualBookDto = new BookDto(book);

        when(bookDao.find(book)).thenReturn(Optional.of(book));
        BookDto savedBookDto = service.saveBook(book);

        verify(bookDao, times(1)).update(book);
        assertThat(savedBookDto).usingRecursiveComparison().isEqualTo(actualBookDto);
    }

    @Test
    @DisplayName("выдать ошибку, если редактируемая книга не найдена по идентификатору")
    void shouldThrowExceptionIfBookToSaveNotFoundById() {
        Author author = new Author(1, "Маяковский В.В.");
        Genre genre = new Genre(1, "Проза");
        Book book = new Book(1, "Неоконченное", author, genre);

        when(bookDao.find(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BookServiceSaveBookException.class).isThrownBy(() -> service.saveBook(book));
    }

    @Test
    @DisplayName("удалять книгу")
    void shouldRemoveBook() {
        Author author1 = new Author(1, "Маяковский В.В.");
        Author author2 = new Author(2, "Айзек Азимов");
        Genre genre = new Genre(1, "Проза");
        Book book1 = new Book(1, "Неоконченное", author1, genre);
        Book book2 = new Book(2, "Я, робот", author2, genre);
        List<Book> list = new ArrayList<>(List.of(book1, book2));

        when(bookDao.getById(1)).thenReturn(Optional.of(book1));
        doAnswer(i -> list.remove(0)).when(bookDao).deleteById(1);

        service.removeBook(1);

        assertThat(list).doesNotContain(book1);
    }

    @Test
    @DisplayName("выдать ошибку, если удаляемая книга не найдена по идентификатору")
    void shouldThrowExceptionIfBookToRemoveNotFoundById() {
        when(bookDao.getById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BookServiceRemoveBookException.class).isThrownBy(() -> service.removeBook(1));
    }
}