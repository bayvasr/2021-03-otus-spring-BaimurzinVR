package ru.otus.mylibrary.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.mylibrary.dto.GenreDto;
import ru.otus.mylibrary.exception.GenreServiceAddGenreException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("REST-контроллер жанров книг GenreRestControllerTest должен ")
class GenreRestControllerTest extends RestControllerTest {

    @Test
    @DisplayName("получать все жанры")
    void getGenres() throws Exception {
        GenreDto genreDto1 = GenreDto.builder().id(1).name("Фантастика").build();
        GenreDto genreDto2 = GenreDto.builder().id(2).name("Роман").build();

        mockMvc.perform(getRequestBuilder(""))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(genreDto1, genreDto2))));
    }

    @Test
    @DisplayName("добавлять жанр")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addGenre() throws Exception {
        GenreDto genreDto1 = GenreDto.builder().id(1).name("Фантастика").build();
        GenreDto genreDto2 = GenreDto.builder().id(2).name("Роман").build();
        GenreDto newGenreDto = GenreDto.builder().id(3).name("New genre").build();

        mockMvc.perform(getRequestBuilder(""))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(genreDto1, genreDto2))));

        mockMvc.perform(postRequestBuilder("", "New genre"))
                .andExpect(status().isOk());

        mockMvc.perform(getRequestBuilder(""))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(genreDto1, genreDto2, newGenreDto))));

    }

    @Test
    @DisplayName("вернуть ошибку, если добавляемый жанр уже есть в БД")
    void handleGenreServiceAddGenreException() throws Exception {
        mockMvc.perform(postRequestBuilder("", "Фантастика"))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof GenreServiceAddGenreException));
    }

    @Override
    protected String getBaseUrlTemplate() {
        return "/mylibrary/genres";
    }
}