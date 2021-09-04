package ru.otus.mylibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mylibrary.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    
}