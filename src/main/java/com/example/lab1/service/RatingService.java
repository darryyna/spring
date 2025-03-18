package com.example.lab1.service;

import com.example.lab1.DTO.RatingDTO;
import com.example.lab1.mapper.RatingMapper;
import com.example.lab1.model.Rating;
import com.example.lab1.repository.RatingRepository;
import com.example.lab1.repository.MovieRepository;
import com.example.lab1.repository.UserRepository;
import com.example.lab1.service.customException.DuplicateResourceException;
import com.example.lab1.service.customException.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;
    private final RatingMapper ratingMapper;


    @Autowired
    public RatingService(RatingRepository ratingRepository, MovieRepository movieRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.movieRepository = movieRepository;
        this.ratingMapper = ratingMapper;
    }

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public Rating createRating(RatingDTO ratingDTO) throws DuplicateResourceException, ResourceNotFoundException {
        Rating rating = ratingMapper.toEntity(ratingDTO);
        if (ratingRepository.existsByUserAndMovie(rating.getUser(), rating.getMovie())) {
            throw new DuplicateResourceException("User has already rated this movie.");
        }

        return ratingRepository.save(rating);
    }

    public List<Rating> findRatingsByMovieId(Long movieId) throws ResourceNotFoundException {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Movie with id " + movieId + " not found");
        }
        return ratingRepository.findRatingsByMovie_MovieId(movieId);
    }

    public Rating updateRating(Long ratingId, Rating rating) throws ResourceNotFoundException {
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating with id " + ratingId + " not found"));

        rating.setRatingId(ratingId);
        return ratingRepository.save(existingRating);
    }
}