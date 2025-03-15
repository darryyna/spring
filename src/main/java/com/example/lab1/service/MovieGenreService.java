package com.example.lab1.service;

import com.example.lab1.model.Genre;
import com.example.lab1.model.Movie;
import com.example.lab1.model.MovieGenre;
import com.example.lab1.repository.MovieGenreRepository;
import com.example.lab1.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MovieGenreService {
    private final MovieGenreRepository movieGenreRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public MovieGenreService(MovieGenreRepository movieGenreRepository, MovieRepository movieRepository) {
        this.movieGenreRepository = movieGenreRepository;
        this.movieRepository = movieRepository;
    }

    public List<MovieGenre> findAll() {
        return movieGenreRepository.findAll();
    }

    public MovieGenre createMovieGenre(MovieGenre movieGenre) {
        return movieGenreRepository.save(movieGenre);
    }

    public List<Genre> findGenresByMovieId(Long movieId) {
        return movieGenreRepository.findGenresByMovieId(movieId);
    }

    public Movie findMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Movie not found with id: " + movieId));
    }

    public Movie findMovieByTitle(String title) {
        return movieRepository.findMovieByTitle(title)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Movie not found with title: " + title));
    }

    public List<MovieGenre> findAllMovieGenresByMovieId(Long movieId) {
        return movieGenreRepository.findByMovie_MovieId(movieId);
    }

}