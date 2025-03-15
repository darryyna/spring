package com.example.lab1.repository;

import com.example.lab1.model.Movie;
import com.example.lab1.model.Rating;
import com.example.lab1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByUser(User user);
    List<Rating> findByMovie(Movie movie);
    Optional<Rating> findByUserAndMovie(User user, Movie movie);

//    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.movie.id = :movieId")
//    Double calculateAverageRatingForMovie(Long movieId);
}
