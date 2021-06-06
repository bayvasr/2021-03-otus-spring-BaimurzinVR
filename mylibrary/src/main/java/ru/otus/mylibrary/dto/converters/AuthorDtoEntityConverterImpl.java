package ru.otus.mylibrary.dto.converters;

import org.springframework.stereotype.Component;
import ru.otus.mylibrary.domain.Author;
import ru.otus.mylibrary.dto.AuthorDto;
import ru.otus.mylibrary.dto.DtoConverter;

@Component
public class AuthorDtoEntityConverterImpl implements DtoConverter<AuthorDto, Author> {
    @Override
    public AuthorDto from(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }

    @Override
    public Author to(AuthorDto authorDto) {
        return Author.builder()
                .id(authorDto.getId())
                .name(authorDto.getName())
                .build();
    }
}
