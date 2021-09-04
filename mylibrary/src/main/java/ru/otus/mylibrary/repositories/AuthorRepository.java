package ru.otus.mylibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mylibrary.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

}