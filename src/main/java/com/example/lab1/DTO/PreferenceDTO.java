package com.example.lab1.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class PreferenceDTO {
    @NotEmpty
    private String username;
    @NotNull
    private List<GenreDTO> genres;
    @Positive
    private Integer preferredMaxDuration;
    @Past
    private Integer preferredMinYear;
    @PastOrPresent
    private Integer preferredMaxYear;
    @Positive
    @Max(10)
    private Double preferredMaxRating;
}