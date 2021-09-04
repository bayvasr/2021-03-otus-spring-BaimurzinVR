package ru.otus.mylibrary.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreDto {
    private long id;
    private String name;

    @Override
    public String toString() {
        return String.format("%d. %s", id, name);
    }
}
