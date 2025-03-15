package com.example.lab1.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class MovieGenreDTO {
    @NotEmpty
    private String movieTitle;
    @NotEmpty
    private List<GenreDTO> genres;
}
