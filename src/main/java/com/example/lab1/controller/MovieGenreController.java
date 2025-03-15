package com.example.lab1.controller;

import com.example.lab1.model.Genre;
import com.example.lab1.model.MovieGenre;
import com.example.lab1.service.MovieGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieGenres")
public class MovieGenreController {
    private final MovieGenreService movieGenreService;

    @Autowired
    public MovieGenreController(MovieGenreService movieGenreService) {
        this.movieGenreService = movieGenreService;
    }

    @GetMapping
    public ResponseEntity<List<MovieGenre>> getAllMovieGenres() {
        return ResponseEntity.ok(movieGenreService.findAll());
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Genre>> getGenresByMovieId(@PathVariable Long movieId) {
        List<Genre> genres = movieGenreService.findGenresByMovieId(movieId);
        return ResponseEntity.ok(genres);
    }


    @PostMapping
    public ResponseEntity<MovieGenre> createMovieGenre(@RequestBody MovieGenre movieGenre) {
        MovieGenre createdMovieGenre = movieGenreService.createMovieGenre(movieGenre);
        return new ResponseEntity<>(createdMovieGenre, HttpStatus.CREATED);
    }
}
