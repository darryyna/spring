package com.example.lab1.mapper;

import com.example.lab1.DTO.RatingDTO;
import com.example.lab1.model.Rating;
import com.example.lab1.model.User;
import com.example.lab1.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "movie.title", target = "movieTitle")
    RatingDTO toDTO(Rating rating);

    @Mapping(target = "user", expression = "java(findUserByUsername(ratingDTO.getUsername()))")
    @Mapping(target = "movie", expression = "java(findMovieByTitle(ratingDTO.getMovieTitle()))")
    Rating toEntity(RatingDTO ratingDTO);

    User findUserByUsername(String username);

    Movie findMovieByTitle(String title);
}
