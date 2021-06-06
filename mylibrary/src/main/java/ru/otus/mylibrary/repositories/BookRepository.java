package ru.otus.mylibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mylibrary.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    
}
