package com.example.lab1.mapper;

import com.example.lab1.DTO.GenreDTO;
import com.example.lab1.DTO.MovieGenreDTO;
import com.example.lab1.model.Genre;
import com.example.lab1.model.Movie;
import com.example.lab1.model.MovieGenre;
import com.example.lab1.service.GenreService;
import com.example.lab1.service.MovieService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieGenreMapper {

    @Mapping(source = "movie.title", target = "movieTitle")
    @Mapping(source = "genres", target = "genres", qualifiedByName = "genresToDTOs")
    MovieGenreDTO toDTO(Movie movie, List<Genre> genres);

    @Named("genresToDTOs")
    default List<GenreDTO> genresToDTOs(List<Genre> genres) {
        if (genres == null) {
            return null;
        }
        return genres.stream()
                .map(genre -> {
                    GenreDTO genreDTO = new GenreDTO();
                    genreDTO.setName(genre.getName());
                    genreDTO.setDescription(genre.getDescription());
                    return genreDTO;
                })
                .collect(Collectors.toList());
    }

//    // Перетворення MovieGenreDTO в сутність MovieGenre
//    @Mapping(source = "movieTitle", target = "movie")
//    @Mapping(source = "genres", target = "genre")
//    MovieGenre toEntity(MovieGenreDTO movieGenreDTO, @Context MovieService movieService, @Context GenreService genreService);

}

