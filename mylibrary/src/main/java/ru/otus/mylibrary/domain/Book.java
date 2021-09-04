package ru.otus.mylibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
@Entity
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Author.class)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "author_id")
    private Author author;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Genre.class)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                '}';
    }
}
