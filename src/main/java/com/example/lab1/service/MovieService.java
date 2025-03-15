package com.example.lab1.service;

import com.example.lab1.model.Movie;
import com.example.lab1.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public List<Movie> findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public Optional<Movie> findMovieByTitle(String title) {
        return movieRepository.findMovieByTitle(title);
    }

    public List<Movie> findByMinimumRating(Double minRating) {
        return movieRepository.findByMinimumRating(minRating);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movie) {
        Optional<Movie> existingMovie = movieRepository.findById(id);
        if (existingMovie.isPresent()) {
            movie.setMovieId(id);
            return movieRepository.save(movie);
        } else {
            throw new RuntimeException("Movie with id " + id + " not found");
        }
    }

    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

}