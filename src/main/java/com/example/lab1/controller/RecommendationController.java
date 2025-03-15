package com.example.lab1.controller;

import com.example.lab1.model.Recommendation;
import com.example.lab1.service.RecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getRecommendationsByUser(@PathVariable Long userId) {
        List<Recommendation> recommendations = recommendationService.findByUser(userId) ;
        return recommendations.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(recommendations);
    }

    @PostMapping
    public ResponseEntity<Recommendation> createRecommendation(@RequestBody Recommendation recommendation) {
        Recommendation createdRecommendation = recommendationService.createRecommendation(recommendation);
        return new ResponseEntity<>(createdRecommendation, HttpStatus.CREATED);
    }
}

