package ru.otus.mylibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mylibrary.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    
}
