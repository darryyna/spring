package com.example.lab1.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor
public class GenreDTO {
    @NotEmpty
    private String name;
}
