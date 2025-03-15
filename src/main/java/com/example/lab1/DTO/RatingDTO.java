package com.example.lab1.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class RatingDTO {
    @Positive
    @Max(10)
    @NotNull
    private Double score;
    private String comment;
    @PastOrPresent
    private LocalDateTime ratingDate;
    @NotEmpty
    private String username;
    @NotEmpty
    private String movieTitle;
}
