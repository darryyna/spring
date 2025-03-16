package com.example.lab1.service;

import com.example.lab1.model.Genre;
import com.example.lab1.model.Movie;
import com.example.lab1.model.MovieGenre;
import com.example.lab1.repository.MovieGenreRepository;
import com.example.lab1.repository.MovieRepository;
import com.example.lab1.service.customException.DuplicateResourceException;
import com.example.lab1.service.customException.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public MovieGenre createMovieGenre(MovieGenre movieGenre) throws DuplicateResourceException {
        if (movieGenreRepository.existsByMovieAndGenre(movieGenre.getMovie(), movieGenre.getGenre())) {
            throw new DuplicateResourceException("MovieGenre already exists");
        }
        return movieGenreRepository.save(movieGenre);
    }

    public List<Genre> findGenresByMovieId(Long movieId) throws ResourceNotFoundException {
        if (movieRepository.findById(movieId).isEmpty()) {
            throw new ResourceNotFoundException("Movie not found with id: " + movieId);
        }
        return movieGenreRepository.findGenresByMovieId(movieId);
    }

    public Movie findMovieById(Long movieId) throws ResourceNotFoundException {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + movieId));
    }

    public Movie findMovieByTitle(String title) throws ResourceNotFoundException {
        return movieRepository.findMovieByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with title: " + title));
    }

    public List<MovieGenre> findAllMovieGenresByMovieId(Long movieId) throws ResourceNotFoundException {
        if (movieRepository.findById(movieId).isEmpty()) {
            throw new ResourceNotFoundException("Movie not found with id: " + movieId);
        }
        return movieGenreRepository.findByMovie_MovieId(movieId);
    }

}