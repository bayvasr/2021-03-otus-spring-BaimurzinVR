package ru.otus.mylibrary.dto.converters;

import org.springframework.stereotype.Component;
import ru.otus.mylibrary.domain.Genre;
import ru.otus.mylibrary.dto.DtoConverter;
import ru.otus.mylibrary.dto.GenreDto;

@Component
public class GenreDtoEntityConverterImpl implements DtoConverter<GenreDto, Genre> {

    @Override
    public GenreDto from(Genre genre) {
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

    @Override
    public Genre to(GenreDto genreDto) {
        return Genre.builder()
                .id(genreDto.getId())
                .name(genreDto.getName())
                .build();
    }
}
