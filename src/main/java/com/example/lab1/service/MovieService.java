package com.example.lab1.service;

import com.example.lab1.model.Movie;
import com.example.lab1.repository.MovieRepository;
import com.example.lab1.service.customException.DuplicateResourceException;
import com.example.lab1.service.customException.ResourceNotFoundException;
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

    public List<Movie> findByTitle(String title) throws ResourceNotFoundException {
        if(movieRepository.findByTitle(title).isEmpty()) {
            throw new ResourceNotFoundException("Movie with title " + title + " not found");
        }
        return movieRepository.findByTitle(title);
    }

    public Optional<Movie> findMovieByTitle(String title) throws ResourceNotFoundException {
        if(movieRepository.findMovieByTitle(title).isEmpty()) {
            throw new ResourceNotFoundException("Movie with title " + title + " not found");
        }
        return movieRepository.findMovieByTitle(title);
    }

    public List<Movie> findByMinimumRating(Double minRating) throws ResourceNotFoundException {
        if(movieRepository.findByMinimumRating(minRating).isEmpty()) {
            throw new ResourceNotFoundException("Movie with minimum rating " + minRating + " not found");
        }
        return movieRepository.findByMinimumRating(minRating);
    }

    public Movie save(Movie movie) throws DuplicateResourceException {
        List<Movie> existingMovies = movieRepository.findByTitle(movie.getTitle());
        if (!existingMovies.isEmpty()) {
            for (Movie existingMovie : existingMovies) {
                if (existingMovie.getTitle().equals(movie.getTitle()) &&
                        existingMovie.getReleaseDate().equals(movie.getReleaseDate())) {
                    throw new DuplicateResourceException("Movie with title '" + movie.getTitle() +
                            "' and release date '" + movie.getReleaseDate() + "' already exists");
                }
            }
        }
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movie) throws ResourceNotFoundException {
        Optional<Movie> existingMovie = movieRepository.findById(id);
        if (existingMovie.isPresent()) {
            movie.setMovieId(id);
            return movieRepository.save(movie);
        } else {
            throw new ResourceNotFoundException("Movie with id " + id + " not found");
        }
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        Optional<Movie> existingMovie = movieRepository.findById(id);
        if (existingMovie.isEmpty()) {
            throw new ResourceNotFoundException("Movie with id " + id + " not found");
        }
        movieRepository.deleteById(id);
    }

    public Movie findMovieByMovieId(Long movieId) throws ResourceNotFoundException {
        if(movieRepository.findByMovieId(movieId) == null) {
            throw new ResourceNotFoundException("Movie with id " + movieId + " not found");
        }
        return movieRepository.findByMovieId(movieId);
    }

}