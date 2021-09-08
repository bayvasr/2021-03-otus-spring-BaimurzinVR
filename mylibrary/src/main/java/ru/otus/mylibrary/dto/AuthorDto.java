package ru.otus.mylibrary.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthorDto {
    private long id;
    private String name;

    @Override
    public String toString() {
        return String.format("%d. %s", id, name);
    }
    

}
