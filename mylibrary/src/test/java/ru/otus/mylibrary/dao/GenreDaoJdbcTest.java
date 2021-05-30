package ru.otus.mylibrary.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.mylibrary.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.BEFORE_METHOD;

@JdbcTest
@Import(GenreDaoJdbc.class)
@DisplayName("Класс GenreDaoJdbc должен")
class GenreDaoJdbcTest {
    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("добавлять жанр в базу данных")
    void shouldInsertGenre() {
        Genre genre = new Genre("Проза");
        Genre insertedGenre = genreDao.insert(genre);
        assertThat(insertedGenre.getId()).isPositive();
        assertThat(insertedGenre.getName()).isEqualTo(genre.getName());
    }

    @Test
    @DisplayName("находить жанр в базе данных")
    void shouldFindGenre() {
        Genre genre = new Genre("Проза");
        genreDao.insert(genre);
        Genre foundGenre = genreDao.find(genre).orElse(Genre.UNKNOWN_GENRE);
        assertThat(foundGenre.getId()).isPositive();
        assertThat(foundGenre.getName()).isEqualTo(genre.getName());
    }

    @Test
    @DirtiesContext(methodMode = BEFORE_METHOD)
    @DisplayName("получать все жанры из базы данных")
    void shouldGetAllGenres() {
        Genre genre = new Genre("Проза");
        Genre insertedGenre = genreDao.insert(genre);
        Genre genre2 = new Genre("Поэма");
        Genre insertedGenre2 = genreDao.insert(genre2);
        List<Genre> genreList = List.of(insertedGenre, insertedGenre2);
        List<Genre> getGenres = genreDao.getAll();
        assertThat(getGenres.size()).isEqualTo(4);
        assertThat(getGenres).containsAll(genreList);
    }

    @Test
    @DisplayName("находить жанр в базе данных по идентификатору")
    void shouldGetGenreById() {
        Genre expGenre = new Genre("Комедия");
        Genre insGenre = genreDao.insert(expGenre);
        Genre actGenre = genreDao.getById(insGenre.getId()).orElse(Genre.UNKNOWN_GENRE);
        assertThat(actGenre.getName()).isEqualTo(expGenre.getName());
    }

}