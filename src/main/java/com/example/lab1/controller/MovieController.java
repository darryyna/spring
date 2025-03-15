package com.example.lab1.controller;

import com.example.lab1.DTO.MovieDTO;
import com.example.lab1.mapper.MovieMapper;
import com.example.lab1.model.Movie;
import com.example.lab1.model.MovieGenre;
import com.example.lab1.model.Rating;
import com.example.lab1.service.MovieGenreService;
import com.example.lab1.service.MovieService;
import com.example.lab1.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieService.findAll();
        return movies.stream()
                .map(movie -> {
                    List<MovieGenre> movieGenres = movieGenreService.findAllMovieGenresByMovieId(movie.getMovieId());
                    List<Rating> ratings = ratingService.findRatingsByMovieId(movie.getMovieId());
                    return movieMapper.toDTO(movie, movieGenres, ratings);
                })
                .toList();
    }

    @GetMapping("/{title}")
    public List<MovieDTO> getMoviesByTitle(@PathVariable String title) {
        List<Movie> movies = movieService.findByTitle(title);
        return movies.stream()
                .map(movie -> {
                    List<MovieGenre> movieGenres = movieGenreService.findAllMovieGenresByMovieId(movie.getMovieId());
                    List<Rating> ratings = ratingService.findRatingsByMovieId(movie.getMovieId());
                    return movieMapper.toDTO(movie, movieGenres, ratings);
                })
                .toList();
    }

    @GetMapping("/rating/{minRating}")
    public List<MovieDTO> getMoviesByMinimumRating(@PathVariable Double minRating) {
        List<Movie> movies = movieService.findByMinimumRating(minRating);
        return movies.stream()
                .map(movie -> {
                    List<MovieGenre> movieGenres = movieGenreService.findAllMovieGenresByMovieId(movie.getMovieId());
                    List<Rating> ratings = ratingService.findRatingsByMovieId(movie.getMovieId());
                    return movieMapper.toDTO(movie, movieGenres, ratings);
                })
                .toList();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.save(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setMovieId(id);
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDuration(movieDTO.getDuration());
        movie.setAverageRating(movieDTO.getAverageRating());
        Movie updatedMovie = movieService.updateMovie(id, movie);

        return ResponseEntity.ok(movieMapper.toDTO(updatedMovie, List.of(), List.of()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
