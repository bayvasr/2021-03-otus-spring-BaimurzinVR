package ru.otus.mylibrary.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.mylibrary.dto.BookDto;
import ru.otus.mylibrary.dto.CommentDto;
import ru.otus.mylibrary.exception.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
@DisplayName("REST-контроллер книг BookRestControllerTest должен ")
class BookRestControllerTest extends RestControllerTest {

//    public static final String MYLIBRARY_BOOKS = "/mylibrary/books";
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;

    @Test
    @DisplayName("вернуть все книги")
    void getAllBooks() throws Exception {
        BookDto bookDto1 = BookDto.builder()
                .id(1)
                .title("Я, робот")
                .author("Айзек Азимов")
                .genre("Фантастика")
                .build();
        BookDto bookDto2 = BookDto.builder()
                .id(2)
                .title("Пикник на обочине")
                .author("неизвестен")
                .genre("Фантастика")
                .build();
        BookDto bookDto3 = BookDto.builder()
                .id(3)
                .title("Отцы и дети")
                .author("неизвестен")
                .genre("неизвестен")
                .build();
        List<BookDto> list = List.of(bookDto1, bookDto2, bookDto3);

        mockMvc.perform(getRequestBuilder(""))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }

    @Test
    @DisplayName("вернуть книгу по идентификатору")
    void getBook() throws Exception {
        BookDto bookDto = BookDto.builder()
                .id(2)
                .title("Пикник на обочине")
                .author("неизвестен")
                .genre("Фантастика")
                .build();
        mockMvc.perform(getRequestBuilder("/2"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookDto)));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("добавить книгу")
    void addBook() throws Exception {
        BookDto bookDto = BookDto.builder()
                .id(0)
                .title("Неоконченное")
                .author("Маяковский В.В.")
                .genre("Проза")
                .build();

        BookDto expectedBookDto = BookDto.builder()
                .id(4)
                .title("Неоконченное")
                .author("Маяковский В.В.")
                .genre("Проза")
                .build();

        mockMvc.perform(postRequestBuilder("", objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBookDto)));

        mockMvc.perform(getRequestBuilder("/4"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBookDto)));

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("отредактировать книгу")
    void editBook() throws Exception {
        BookDto origBookDto = BookDto.builder()
                .id(3)
                .title("Отцы и дети")
                .author("неизвестен")
                .genre("неизвестен")
                .build();

        BookDto editedBookDto = BookDto.builder()
                .id(3)
                .title("Отцы и дети")
                .author("Тургенев И.С.")
                .genre("Роман")
                .build();

        mockMvc.perform(getRequestBuilder("/3"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(origBookDto)));

        mockMvc.perform(putRequestBuilder("", objectMapper.writeValueAsString(editedBookDto)))
                .andExpect(status().isOk());

        mockMvc.perform(getRequestBuilder("/3"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(editedBookDto)));

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("удалить книгу")
    void deleteBook() throws Exception {
        BookDto origBookDto = BookDto.builder()
                .id(3)
                .title("Отцы и дети")
                .author("неизвестен")
                .genre("неизвестен")
                .build();

        mockMvc.perform(getRequestBuilder("/3"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(origBookDto)));

        mockMvc.perform(deleteRequestBuilder("/3"))
                .andExpect(status().isOk());
        mockMvc.perform(getRequestBuilder("/3"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof BookServiceFindBookException
                ));

    }

    @Test
    @DisplayName("вернуть все комментарии по книге")
    void getBookComments() throws Exception {
        CommentDto new_comment = CommentDto.builder()
                .bookId(1)
                .bookTitle("Я, робот")
                .text("New comment")
                .id(1)
                .build();

        CommentDto old_comment = CommentDto.builder()
                .bookId(1)
                .bookTitle("Я, робот")
                .text("Old comment")
                .id(2)
                .build();

        List<CommentDto> comments = List.of(new_comment, old_comment);

        mockMvc.perform(getRequestBuilder("/1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(comments)));
    }

    @Test
    @DisplayName("добавить комментарий к книге")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addBookComment() throws Exception {

        CommentDto new_comment = CommentDto.builder()
                .bookId(1)
                .bookTitle("Я, робот")
                .text("New comment")
                .id(1)
                .build();

        CommentDto old_comment = CommentDto.builder()
                .bookId(1)
                .bookTitle("Я, робот")
                .text("Old comment")
                .id(2)
                .build();

        CommentDto added_comment = CommentDto.builder()
                .bookId(1)
                .bookTitle("Я, робот")
                .text("Very newest comment")
                .id(3)
                .build();

        List<CommentDto> comments = List.of(new_comment, old_comment, added_comment);

        mockMvc.perform(postRequestBuilder("/1/comments", "Very newest comment"))
                .andExpect(status().isOk());

        mockMvc.perform(getRequestBuilder("/1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(comments)));

    }

    @Test
    @DisplayName("удалить комментарий к книге")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteBookComment() throws Exception {
        CommentDto new_comment = CommentDto.builder()
                .bookId(1)
                .bookTitle("Я, робот")
                .text("New comment")
                .id(1)
                .build();

        CommentDto old_comment = CommentDto.builder()
                .bookId(1)
                .bookTitle("Я, робот")
                .text("Old comment")
                .id(2)
                .build();


        List<CommentDto> comments = List.of(new_comment, old_comment);

        mockMvc.perform(getRequestBuilder("/1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(comments)));

        mockMvc.perform(deleteRequestBuilder("/1/comments/2"))
                .andExpect(status().isOk());


        mockMvc.perform(getRequestBuilder("/1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(new_comment))));
    }

    @Test
    @DisplayName("вернуть ошибку, добавляемая книга уже есть в БД")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void handleBookServiceAddBookException() throws Exception {
        BookDto bookDto1 = BookDto.builder()
                .id(0)
                .title("Я, робот")
                .author("Айзек Азимов")
                .genre("Фантастика_new")
                .build();

        mockMvc.perform(postRequestBuilder("", objectMapper.writeValueAsString(bookDto1)))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookServiceAddBookException));
    }

    @Test
    @DisplayName("вернуть ошибку, если книга не найдена")
    void handleBookServiceFindBookException() throws Exception {
        mockMvc.perform(getRequestBuilder("/33"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookServiceFindBookException));
    }

    @Test
    @DisplayName("вернуть ошибку, если удаляемая книга не найдена")
    void handleBookServiceRemoveBookException() throws Exception {
        mockMvc.perform(deleteRequestBuilder("/33"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookServiceRemoveBookException));
    }

    @Test
    @DisplayName("вернуть ошибку, если редактируемая книга не найдена")
    void handleBookServiceUpdateBookException() throws Exception {
        BookDto bookDto1 = BookDto.builder()
                .id(55)
                .title("Я, робот!!!")
                .author("Айзек Азимов")
                .genre("Фантастика")
                .build();
        mockMvc.perform(putRequestBuilder("", objectMapper.writeValueAsString(bookDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookServiceUpdateBookException));
    }

    @Test
    @DisplayName("вернуть ошибку, если удаляемый комментарий к книге не найден")
    void handleCommentServiceNotFoundException() throws Exception {
        mockMvc.perform(deleteRequestBuilder("/1/comments/66"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CommentServiceNotFoundException));
    }

    @Override
    protected String getBaseUrlTemplate() {
        return "/mylibrary/books";
    }
}