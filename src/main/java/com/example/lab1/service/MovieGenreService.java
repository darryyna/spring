package com.example.lab1.service;

import com.example.lab1.model.Genre;
import com.example.lab1.model.MovieGenre;
import com.example.lab1.repository.MovieGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieGenreService {
    private final MovieGenreRepository movieGenreRepository;

    @Autowired
    public MovieGenreService(MovieGenreRepository movieGenreRepository) {
        this.movieGenreRepository = movieGenreRepository;
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
}