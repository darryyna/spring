package com.example.lab1.mapper;

import com.example.lab1.DTO.GenreDTO;
import com.example.lab1.DTO.MovieGenreDTO;
import com.example.lab1.model.Genre;
import com.example.lab1.model.Movie;
import com.example.lab1.model.MovieGenre;
import com.example.lab1.service.GenreService;
import com.example.lab1.service.MovieService;
import com.example.lab1.service.customException.ResourceNotFoundException;
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
                    return genreDTO;
                })
                .collect(Collectors.toList());
    }

    @Mapping(source = "movieTitle", target = "movie", qualifiedByName = "movieTitleToMovie")
    @Mapping(source = "genres", target = "genre", qualifiedByName = "genreDTOsToGenre")
    MovieGenre toEntity(MovieGenreDTO movieGenreDTO, @Context MovieService movieService, @Context GenreService genreService);

    @Named("movieTitleToMovie")
    default Movie movieTitleToMovie(String movieTitle, @Context MovieService movieService) throws ResourceNotFoundException {
        List<Movie> movies = movieService.findByTitle(movieTitle);
        if (movies != null && !movies.isEmpty()) {
            return movies.get(0);
        } else {
            return null;
        }
    }

    @Named("genreDTOsToGenre")
    default Genre genreDTOsToGenre(List<GenreDTO> genreDTOs, @Context GenreService genreService) throws ResourceNotFoundException {
        if (genreDTOs == null || genreDTOs.isEmpty()) {
            return null;
        }
        return genreService.findByName(genreDTOs.get(0).getName());
    }
}