package com.example.lab1.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
public class MovieDTO {
    @NotEmpty
    private String title;
    @NotNull
    private String description;
    private LocalDate releaseDate;
    @NotNull
    private Integer duration;
    private Double averageRating;
    private List<GenreDTO> genres;
    private List<RatingDTO> ratings;
}
