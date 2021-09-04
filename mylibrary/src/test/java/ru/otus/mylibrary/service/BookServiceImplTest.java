package ru.otus.mylibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Comment;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.*;
import ru.otus.mylibrary.dto.converters.BookDtoEntityConverterImpl;
import ru.otus.mylibrary.dto.converters.CommentDtoEntityConverterImpl;
import ru.otus.mylibrary.exception.BookServiceAddBookException;
import ru.otus.mylibrary.exception.BookServiceRemoveBookException;
import ru.otus.mylibrary.exception.BookServiceUpdateBookException;
import ru.otus.mylibrary.repositories.AuthorRepository;
import ru.otus.mylibrary.repositories.BookRepository;
import ru.otus.mylibrary.repositories.GenreRepository;

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
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private CommentService commentService;

    @Mock
    private AuthorService authorService;
    @Mock
    private GenreService genreService;

    private DtoConverter<BookDto, Book> dtoConverter;
    private DtoConverter<CommentDto, Comment> commentDtoConverter = new CommentDtoEntityConverterImpl();

    @BeforeEach
    void init() {
        dtoConverter = new BookDtoEntityConverterImpl(authorService, genreService);
        service = new BookServiceImpl(bookRepository, authorRepository, genreRepository, commentService,
                dtoConverter, commentDtoConverter);

    }

    @Test
    @DisplayName("получать все книги")
    void shouldGetAllBooks() {
        Author author1 = new Author(1, "Маяковский В.В.");
        Author author2 = new Author(2, "Айзек Азимов");
        Genre genre1 = new Genre(1, "Проза");
        Genre genre2 = new Genre(1, "Фантастика");
        Book book1 = new Book(1, "Неоконченное", author1, genre1, new ArrayList<>());
        Book book2 = new Book(2, "Я, робот", author2, genre2, new ArrayList<>());

        List<Book> list = List.of(book1, book2);

        BookDto dto1 = dtoConverter.from(book1);
        BookDto dto2 = dtoConverter.from(book2);

        when(bookRepository.findAll()).thenReturn(list);

        List<BookDto> actList = service.getAllBooks();

        assertThat(actList.get(0)).usingRecursiveComparison().isEqualTo(dto1);
        assertThat(actList.get(1)).usingRecursiveComparison().isEqualTo(dto2);
    }

    @Test
    @DisplayName("добавлять новую книгу")
    void shouldAddBook() {
        Author author = new Author(1, "Маяковский В.В.");
        Genre genre = new Genre(1, "Проза");
        Book book = new Book(1, "Неоконченное", author, genre, new ArrayList<>());

        BookDto daoInsertedBookDto = dtoConverter.from(book);

        BookDto bookDto = BookDto.builder()
                .title("Неоконченное")
                .author("Маяковский В.В.")
                .genre("Проза")
                .build();

        when(bookRepository.exists(any())).thenReturn(false);
        when(bookRepository.save(any())).thenReturn(book);

        BookDto addedDto = service.addBook(bookDto);

        assertThat(addedDto).usingRecursiveComparison().isEqualTo(daoInsertedBookDto);
    }

    @Test
    @DisplayName("выдать ошибку, если добавляемая книга уже есть в базе данных")
    void shouldThrowExceptionIfBookToAddNotFoundById() {
        BookDto bookDto = BookDto.builder()
                .title("Неоконченное")
                .author("Маяковский В.В.")
                .genre("Проза")
                .build();

        when(bookRepository.exists(any())).thenReturn(true);

        assertThatExceptionOfType(BookServiceAddBookException.class).isThrownBy(() -> service.addBook(bookDto));
    }

    @Test
    @DisplayName("сохранять отредактированную книгу")
    void shouldUpdateBook() {
        Author author = new Author(1, "Маяковский В.В.");
        Genre genre = new Genre(1, "Проза");
        Book book = new Book(1, "Неоконченное", author, genre, new ArrayList<>());
        BookDto actualBookDto = dtoConverter.from(book);

        when(bookRepository.existsById(book.getId())).thenReturn(true);
        when(bookRepository.save(any())).thenReturn(book);
        when(authorService.findAuthorByName(author.getName())).thenReturn(
                Optional.ofNullable(AuthorDto.builder().id(1).name("Маяковский В.В.").build())
        );
        when(genreService.findGenreByName("Проза")).thenReturn(
                Optional.ofNullable(GenreDto.builder().id(1).name("Проза").build())
        );
        BookDto savedBookDto = service.updateBook(actualBookDto);

        verify(bookRepository, times(1)).save(book);
        assertThat(savedBookDto).usingRecursiveComparison().isEqualTo(actualBookDto);
    }

    @Test
    @DisplayName("выдать ошибку, если редактируемая книга не найдена по идентификатору")
    void shouldThrowExceptionIfBookToUpdateNotFoundById() {
        Author author = new Author(1, "Маяковский В.В.");
        Genre genre = new Genre(1, "Проза");
        Book book = new Book(1, "Неоконченное", author, genre, new ArrayList<>());
        BookDto bookDto = dtoConverter.from(book);

        when(bookRepository.existsById(book.getId())).thenReturn(false);

        assertThatExceptionOfType(BookServiceUpdateBookException.class).isThrownBy(() -> service.updateBook(bookDto));
    }

    @Test
    @DisplayName("удалять книгу")
    void shouldRemoveBook() {
        Author author1 = new Author(1, "Маяковский В.В.");
        Author author2 = new Author(2, "Айзек Азимов");
        Genre genre = new Genre(1, "Проза");
        Book book1 = new Book(1, "Неоконченное", author1, genre, new ArrayList<>());
        Book book2 = new Book(2, "Я, робот", author2, genre, new ArrayList<>());
        List<Book> list = new ArrayList<>(List.of(book1, book2));

        when(bookRepository.existsById(1L)).thenReturn(true);
        doAnswer(i -> list.remove(0)).when(bookRepository).deleteById(1L);

        service.removeBook(1);

        assertThat(list).doesNotContain(book1);
    }

    @Test
    @DisplayName("выдать ошибку, если удаляемая книга не найдена по идентификатору")
    void shouldThrowExceptionIfBookToRemoveNotFoundById() {
        when(bookRepository.existsById(anyLong())).thenReturn(false);

        assertThatExceptionOfType(BookServiceRemoveBookException.class).isThrownBy(() -> service.removeBook(1));
    }
}