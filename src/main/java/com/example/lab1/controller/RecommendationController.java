package com.example.lab1.controller;

import com.example.lab1.DTO.RecommendationDTO;
import com.example.lab1.model.Movie;
import com.example.lab1.model.Recommendation;
import com.example.lab1.model.User;
import com.example.lab1.mapper.RecommendationMapper;
import com.example.lab1.service.MovieService;
import com.example.lab1.service.RecommendationService;
import com.example.lab1.service.UserService;
import com.example.lab1.service.customException.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final UserService userService;
    private final MovieService movieService;
    private final RecommendationMapper recommendationMapper;

    public RecommendationController(
            RecommendationService recommendationService,
            UserService userService,
            MovieService movieService,
            RecommendationMapper recommendationMapper) {
        this.recommendationService = recommendationService;
        this.userService = userService;
        this.movieService = movieService;
        this.recommendationMapper = recommendationMapper;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationDTO>> getRecommendationsByUser(@PathVariable Long userId) throws ResourceNotFoundException {
        List<Recommendation> recommendations = recommendationService.findByUser(userId);
        if (recommendations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(recommendationMapper.toDTOList(recommendations));
    }

//    @GetMapping("/username/{username}")
//    public ResponseEntity<List<RecommendationDTO>> getRecommendationsByUsername(@PathVariable String username) throws ResourceNotFoundException {
//        Optional<User> userOptional = userService.findByUsername(username);
//        if (userOptional.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        List<Recommendation> recommendations = recommendationService.findByUser(userOptional.get().getUserId());
//        if (recommendations.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        return ResponseEntity.ok(recommendationMapper.toDTOList(recommendations));
//    }

    @PostMapping
    public ResponseEntity<RecommendationDTO> createRecommendation(@RequestBody RecommendationDTO recommendationDTO) throws ResourceNotFoundException {
        Optional<User> userOptional = userService.findByUsername(recommendationDTO.getUsername());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Movie> movieOptional = movieService.findMovieByTitle(recommendationDTO.getMovieTitle());
        if (movieOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Recommendation recommendation = recommendationMapper.toEntity(recommendationDTO);
        recommendation.setUser(userOptional.get());
        recommendation.setMovie(movieOptional.get());

        Recommendation createdRecommendation = recommendationService.createRecommendation(recommendation);

        return new ResponseEntity<>(recommendationMapper.toDTO(createdRecommendation), HttpStatus.CREATED);
    }
}