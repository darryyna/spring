package com.example.lab1.repository;

import com.example.lab1.model.Movie;
import com.example.lab1.model.Rating;
import com.example.lab1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findRatingsByMovie_MovieId(Long movieId);

    boolean existsByUserAndMovie(User user, Movie movie);
}
