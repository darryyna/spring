package com.example.lab1.repository;

import com.example.lab1.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitle(String title);

    Optional<Movie> findMovieByTitle(String title);
    @Query("SELECT m FROM Movie m WHERE m.averageRating >= :minRating")
    List<Movie> findByMinimumRating(Double minRating);

}
