package ru.otus.mylibrary.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.mylibrary.dto.AuthorDto;
import ru.otus.mylibrary.exception.AuthorServiceAddAuthorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("REST-контроллер авторов книг AuthorRestControllerTest должен ")
class AuthorRestControllerTest extends RestControllerTest {

    @Test
    @DisplayName("получать всех авторов")
    void getAuthors() throws Exception {

        AuthorDto authorDto1 = AuthorDto.builder().id(1).name("Айзек Азимов").build();
        AuthorDto authorDto2 = AuthorDto.builder().id(2).name("Тургенев И.С.").build();

        List<AuthorDto> authorDtoList = List.of(authorDto1, authorDto2);

        mockMvc.perform(getRequestBuilder(""))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(authorDtoList)));

    }

    @Test
    @DisplayName("добавлять автора")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addAuthor() throws Exception {
        AuthorDto newAuthorDto = AuthorDto.builder().id(3).name("New author").build();

        AuthorDto authorDto1 = AuthorDto.builder().id(1).name("Айзек Азимов").build();
        AuthorDto authorDto2 = AuthorDto.builder().id(2).name("Тургенев И.С.").build();

        List<AuthorDto> authorDtoList = List.of(authorDto1, authorDto2);


        mockMvc.perform(getRequestBuilder(""))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(authorDtoList)));

        mockMvc.perform(postRequestBuilder("", "New author"))
                .andExpect(status().isOk());

        mockMvc.perform(getRequestBuilder(""))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(authorDto1, authorDto2, newAuthorDto))));
    }

    @Test
    @DisplayName("вернуть ошибку, если добавляемый автор уже есть в БД")
    void handleAuthorServiceAddAuthorException() throws Exception {
        mockMvc.perform(postRequestBuilder("", "Айзек Азимов"))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AuthorServiceAddAuthorException));
    }

    @Override
    protected String getBaseUrlTemplate() {
        return "/mylibrary/authors";
    }
}