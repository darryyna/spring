package com.example.lab1.mapper;

import com.example.lab1.DTO.GenreDTO;
import com.example.lab1.DTO.MovieDTO;
import com.example.lab1.DTO.RatingDTO;
import com.example.lab1.model.Movie;
import com.example.lab1.model.MovieGenre;
import com.example.lab1.model.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(source = "movie.title", target = "title")
    @Mapping(source = "movie.description", target = "description")
    @Mapping(source = "movie.releaseDate", target = "releaseDate")
    @Mapping(source = "movie.duration", target = "duration")
    @Mapping(source = "movie.averageRating", target = "averageRating")
    @Mapping(source = "movieGenres", target = "genres", qualifiedByName = "movieGenresToDTOs")
    @Mapping(source = "ratings", target = "ratings", qualifiedByName = "ratingsToDTOs")
    MovieDTO toDTO(Movie movie, List<MovieGenre> movieGenres, List<Rating> ratings);

    @Named("movieGenresToDTOs")
    default List<GenreDTO> movieGenresToDTOs(List<MovieGenre> movieGenres) {
        if (movieGenres == null) {
            return null;
        }
        return movieGenres.stream()
                .map(movieGenre -> {
                    GenreDTO genreDTO = new GenreDTO();
                    genreDTO.setName(movieGenre.getGenre().getName());
                    return genreDTO;
                })
                .collect(Collectors.toList());
    }

    @Named("ratingsToDTOs")
    default List<RatingDTO> ratingsToDTOs(List<Rating> ratings) {
        if (ratings == null) {
            return null;
        }
        return ratings.stream()
                .map(rating -> {
                    RatingDTO ratingDTO = new RatingDTO();
                    ratingDTO.setScore(rating.getScore());
                    ratingDTO.setComment(rating.getComment());
                    ratingDTO.setRatingDate(rating.getRatingDate());
                    ratingDTO.setUsername(rating.getUser().getUsername());
                    ratingDTO.setMovieTitle(rating.getMovie().getTitle());
                    return ratingDTO;
                })
                .collect(Collectors.toList());
    }

}
