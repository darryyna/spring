package com.example.lab1.mapper;

import com.example.lab1.DTO.RatingDTO;
import com.example.lab1.model.Rating;
import com.example.lab1.model.User;
import com.example.lab1.model.Movie;
import com.example.lab1.service.UserService;
import com.example.lab1.service.MovieService;
import com.example.lab1.service.customException.ResourceNotFoundException;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "movie.title", target = "movieTitle")
    RatingDTO toDTO(Rating rating);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "movie", ignore = true)
    Rating toEntity(RatingDTO ratingDTO);

    @AfterMapping
    default void setUserAndMovie(@MappingTarget Rating rating, RatingDTO ratingDTO,
                                 @Context UserService userService, @Context MovieService movieService)
            throws ResourceNotFoundException {

        User user = userService.findByUsername(ratingDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + ratingDTO.getUsername()));

        Movie movie = movieService.findByTitle(ratingDTO.getMovieTitle())
                .stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found: " + ratingDTO.getMovieTitle()));

        rating.setUser(user);
        rating.setMovie(movie);
    }
}