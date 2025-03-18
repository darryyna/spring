package com.example.lab1.controller;

import com.example.lab1.DTO.MovieDTO;
import com.example.lab1.mapper.MovieMapper;
import com.example.lab1.model.Movie;
import com.example.lab1.model.MovieGenre;
import com.example.lab1.model.Rating;
import com.example.lab1.service.MovieGenreService;
import com.example.lab1.service.MovieService;
import com.example.lab1.service.RatingService;
import com.example.lab1.service.customException.DuplicateResourceException;
import com.example.lab1.service.customException.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieGenreService movieGenreService;
    private final RatingService ratingService;
    private final MovieMapper movieMapper;

    @Autowired
    public MovieController(MovieService movieService, MovieGenreService movieGenreService, RatingService ratingService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieGenreService = movieGenreService;
        this.ratingService = ratingService;
        this.movieMapper = movieMapper;
    }

    private ResponseEntity<List<MovieDTO>> getListResponseEntity(List<Movie> movies) {
        List<MovieDTO> movieDTOs = movies.stream()
                .map(movie -> {
                    try {
                        List<MovieGenre> movieGenres = movieGenreService.findAllMovieGenresByMovieId(movie.getMovieId());
                        List<Rating> ratings = ratingService.findRatingsByMovieId(movie.getMovieId());
                        return movieMapper.toDTO(movie, movieGenres, ratings);
                    } catch (ResourceNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        return ResponseEntity.ok(movieDTOs);
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<Movie> movies = movieService.findAll();

        return getListResponseEntity(movies);
    }

    @GetMapping("/{title}")
    public ResponseEntity<List<MovieDTO>> getMoviesByTitle(@PathVariable String title) throws ResourceNotFoundException {
        List<Movie> movies = movieService.findByTitle(title);

        List<MovieDTO> movieDTOs = movies.stream()
                .map(movie -> {
                    try {
                        List<MovieGenre> movieGenres = movieGenreService.findAllMovieGenresByMovieId(movie.getMovieId());
                        List<Rating> ratings = ratingService.findRatingsByMovieId(movie.getMovieId());
                        return movieMapper.toDTO(movie, movieGenres, ratings);
                    } catch (ResourceNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        return ResponseEntity.ok(movieDTOs);
    }

    @GetMapping("/rating/{minRating}")
    public ResponseEntity<List<MovieDTO>> getMoviesByMinimumRating(@PathVariable Double minRating) throws ResourceNotFoundException {
        List<Movie> movies = movieService.findByMinimumRating(minRating);

        return getListResponseEntity(movies);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) throws DuplicateResourceException {
        return ResponseEntity.ok(movieService.save(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) throws ResourceNotFoundException, DuplicateResourceException {
        movieService.findMovieByMovieId(id);
        Movie updatedMovie = movieService.updateMovie(id, movie);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) throws ResourceNotFoundException {
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
