package com.example.lab1.controller;

import com.example.lab1.DTO.RatingDTO;
import com.example.lab1.mapper.RatingMapper;
import com.example.lab1.model.Rating;
import com.example.lab1.service.RatingService;
import com.example.lab1.service.customException.DuplicateResourceException;
import com.example.lab1.service.customException.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;
    private final RatingMapper ratingMapper;

    public RatingController(RatingService ratingService, RatingMapper ratingMapper) {
        this.ratingService = ratingService;
        this.ratingMapper = ratingMapper;
    }

    @GetMapping
    public ResponseEntity<List<RatingDTO>> getAllRatings() {
        List<Rating> ratings = ratingService.findAll();
        List<RatingDTO> ratingDTOs = ratings.stream()
                .map(ratingMapper::toDTO)
                .toList();
        return ResponseEntity.ok(ratingDTOs);
    }

    @PostMapping
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO ratingDTO) throws DuplicateResourceException, ResourceNotFoundException {
        Rating createdRating = ratingService.createRating(ratingDTO);
        RatingDTO createdRatingDTO = ratingMapper.toDTO(createdRating);
        return new ResponseEntity<>(createdRatingDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable Long ratingId, @RequestBody Rating rating) throws ResourceNotFoundException {
        Rating updatedRating = ratingService.updateRating(ratingId, rating);
        return ResponseEntity.ok(updatedRating);
    }
}
