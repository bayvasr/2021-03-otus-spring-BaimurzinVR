package ru.otus.mylibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mylibrary.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByName(String name);

}
