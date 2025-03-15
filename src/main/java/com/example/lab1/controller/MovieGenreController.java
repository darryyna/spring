package com.example.lab1.controller;

import com.example.lab1.DTO.MovieGenreDTO;
import com.example.lab1.mapper.MovieGenreMapper;
import com.example.lab1.model.Genre;
import com.example.lab1.model.Movie;
import com.example.lab1.model.MovieGenre;
import com.example.lab1.service.MovieGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movieGenres")
public class MovieGenreController {
    private final MovieGenreService movieGenreService;
    private final MovieGenreMapper movieGenreMapper;

    @Autowired
    public MovieGenreController(MovieGenreService movieGenreService, MovieGenreMapper movieGenreMapper) {
        this.movieGenreService = movieGenreService;
        this.movieGenreMapper = movieGenreMapper;
    }

    @GetMapping
    public ResponseEntity<List<MovieGenreDTO>> getAllMovieGenres() {
        List<MovieGenre> movieGenres = movieGenreService.findAll();

        List<MovieGenreDTO> movieGenreDTOs = movieGenres.stream()
                .map(movieGenre -> {
                    Movie movie = movieGenre.getMovie();
                    List<Genre> genres = movieGenreService.findGenresByMovieId(movie.getMovieId());
                    return movieGenreMapper.toDTO(movie, genres);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(movieGenreDTOs);
    }

    @GetMapping("/movie/{movieId}/details")
    public ResponseEntity<MovieGenreDTO> getMovieWithGenres(@PathVariable Long movieId) {
        Movie movie = movieGenreService.findMovieById(movieId);
        List<Genre> genres = movieGenreService.findGenresByMovieId(movieId);
        MovieGenreDTO dto = movieGenreMapper.toDTO(movie, genres);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/movie/{title}")
    public ResponseEntity<MovieGenreDTO> getMovieWithGenresByTitle(@PathVariable String title) {
        Movie movie = movieGenreService.findMovieByTitle(title);
        List<Genre> genres = movieGenreService.findGenresByMovieId(movie.getMovieId());
        MovieGenreDTO dto = movieGenreMapper.toDTO(movie, genres);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<MovieGenre> createMovieGenre(@RequestBody MovieGenre movieGenre) {
        MovieGenre createdMovieGenre = movieGenreService.createMovieGenre(movieGenre);
        return new ResponseEntity<>(createdMovieGenre, HttpStatus.CREATED);
    }
}

