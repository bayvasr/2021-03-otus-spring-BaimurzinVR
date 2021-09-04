package ru.otus.mylibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.dto.AuthorDto;
import ru.otus.mylibrary.dto.DtoConverter;
import ru.otus.mylibrary.dto.converters.AuthorDtoEntityConverterImpl;
import ru.otus.mylibrary.repositories.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс AuthorServiceImpl должен")
class AuthorServiceImplTest {

    private AuthorService service;
    @Mock
    private AuthorRepository authorRepository;

    private DtoConverter<AuthorDto, Author> dtoConverter = new AuthorDtoEntityConverterImpl();

    @BeforeEach
    void init() {
        service = new AuthorServiceImpl(authorRepository, dtoConverter);
    }

    @Test
    @DisplayName("добавлять автора, если его нет в БД")
    void shouldAddAuthor() {

        AuthorDto author = AuthorDto.builder().name("Маяковский В.В.").build();
        Author daoInsertReturnAuthor = new Author(1, "Маяковский В.В.");

//        when(authorDao.find(author)).thenReturn(Optional.empty());
        when(authorRepository.save(any())).thenReturn(daoInsertReturnAuthor);

        AuthorDto actAuthor = service.saveAuthor(author);

        assertThat(actAuthor).usingRecursiveComparison().isEqualTo(daoInsertReturnAuthor);
    }

//    @Test
//    @DisplayName("возвращать существующего автора, если он есть в БД")
//    void shouldGetAuthorIfFind() {
//
//        Author author = new Author("Маяковский В.В.");
//        Author daoFindReturnAuthor = new Author(1, "Маяковский В.В.");
//
//        when(authorDao.find(author)).thenReturn(Optional.of(daoFindReturnAuthor));
//
//        Author actAuthor = service.saveAuthor(author);
//
//        assertThat(actAuthor).usingRecursiveComparison().isEqualTo(daoFindReturnAuthor);
//    }

    @Test
    @DisplayName("получать всех авторов")
    void shouldGetAllAuthors() {
        Author author1 = new Author(1, "Маяковский В.В.");
        Author author2 = new Author(2, "Айзек Азимов");
        List<Author> list = List.of(author1, author2);

        when(authorRepository.findAll()).thenReturn(list);

        List<AuthorDto> actList = service.getAllAuthors();

        assertThat(actList).containsAll(Stream.of(author1, author2)
                .map(dtoConverter::from)
                .collect(Collectors.toList()));
    }
}