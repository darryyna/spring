package com.example.lab1.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
public class RecommendationDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String movieTitle;
    @NotNull
    @Max(10)
    private Double recommendationScore;
    private Boolean isViewed = false;
}
