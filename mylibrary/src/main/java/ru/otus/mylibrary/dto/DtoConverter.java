package ru.otus.mylibrary.dto;

public interface DtoConverter<DTO, OBJECT> {
    DTO from(OBJECT object);

    OBJECT to(DTO dto);
}
