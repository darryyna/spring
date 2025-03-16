package com.example.lab1.service;

import com.example.lab1.model.Recommendation;
import com.example.lab1.repository.RecommendationRepository;
import com.example.lab1.service.customException.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    @Autowired
    public RecommendationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    public List<Recommendation> findByUser(Long userId) throws ResourceNotFoundException {
        if(recommendationRepository.findByUser_UserId(userId).isEmpty()) {
            throw new ResourceNotFoundException("Recommendation not found for user with id: " + userId);
        }
        return recommendationRepository.findByUser_UserId(userId);
    }

    public Recommendation createRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }
}
