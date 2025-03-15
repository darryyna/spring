package com.example.lab1.mapper;

import com.example.lab1.DTO.GenreDTO;
import com.example.lab1.model.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDTO toDTO(Genre genre);
    Genre toEntity(GenreDTO genreDTO);
    List<GenreDTO> toDTOList(List<Genre> genres);
}
