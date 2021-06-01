package ru.otus.mylibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.domain.Book;
import ru.otus.mylibrary.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Book> insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthorId());
        params.addValue("genre_id", book.getGenreId());
        KeyHolder key = new GeneratedKeyHolder();
        jdbc.update("insert into books(title, author_id, genre_id) " +
                        "values (:title, :author_id, :genre_id)",
                params,
                key);
        if (key.getKey() != null) {
            return Optional.of(new Book(key.getKey().longValue(), book.getTitle(), book.getAuthor(), book.getGenre()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void update(Book book) {
        jdbc.update("update books " +
                        "set title = :title, " +
                        "author_id = :author_id, " +
                        "genre_id = :genre_id " +
                        "where id = :id",
                Map.of(
                        "id", book.getId(),
                        "title", book.getTitle(),
                        "author_id", book.getAuthorId(),
                        "genre_id", book.getGenreId()));
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from books where id = :id", Map.of("id", id));
    }

    @Override
    public Optional<Book> findByExample(Book book) {
        return jdbc.query("select b.id, " +
                        "b.title, " +
                        "a.id as author_id, " +
                        "a.name as author_name, " +
                        "g.id as genre_id, " +
                        "g.name as genre_name " +
                        "from books b " +
                        "left join authors a on a.id = b.author_id " +
                        "left join genres g on g.id = b.genre_id " +
                        "where b.title = :title " +
                        "and b.author_id = :author_id " +
                        "and b.genre_id = :genre_id",
                Map.of("title", book.getTitle(),
                        "author_id", book.getAuthorId(),
                        "genre_id", book.getGenreId()),
                new BookMapper()).stream().findFirst();
    }

    @Override
    public boolean existsByExample(Book book) {
        return Optional.ofNullable(jdbc.queryForObject("select count(1)" +
                        "from books b " +
                        "left join authors a on a.id = b.author_id " +
                        "left join genres g on g.id = b.genre_id " +
                        "where b.title = :title " +
                        "and b.author_id = :author_id " +
                        "and b.genre_id = :genre_id",
                Map.of("title", book.getTitle(),
                        "author_id", book.getAuthorId(),
                        "genre_id", book.getGenreId()),
                Integer.class
        )).orElse(0) > 0;
    }


    @Override
    public Optional<Book> getById(long id) {
        return jdbc.query("select b.id, " +
                "b.title, " +
                "a.id as author_id, " +
                "a.name as author_name, " +
                "g.id as genre_id, " +
                "g.name as genre_name " +
                "from books b " +
                "left join authors a on a.id = b.author_id " +
                "left join genres g on g.id = b.genre_id " +
                "where b.id = :id", Map.of("id", id), new BookMapper()).stream().findFirst();
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.id, " +
                "b.title, " +
                "a.id as author_id, " +
                "a.name as author_name, " +
                "g.id as genre_id, " +
                "g.name as genre_name " +
                "from books b " +
                "left join authors a on a.id = b.author_id " +
                "left join genres g on g.id = b.genre_id", new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = rs.getLong("author_id") > 0 ?
                    Author.builder()
                            .id(rs.getLong("author_id"))
                            .name(rs.getString("author_name")).build() :
                    null;
            Genre genre = rs.getLong("genre_id") > 0 ?
                    new Genre(rs.getLong("genre_id"),
                            rs.getString("genre_name")) :
                    null;
            return new Book(rs.getLong("id"), rs.getString("title"), author, genre);
        }
    }
}