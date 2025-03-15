package com.example.lab1.repository;

import com.example.lab1.model.Recommendation;
import com.example.lab1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findByUser_UserId(Long userId);
}
