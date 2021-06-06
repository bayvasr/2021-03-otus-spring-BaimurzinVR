package ru.otus.mylibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Author.class)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Genre.class)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
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
