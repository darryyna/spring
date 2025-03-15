package com.example.lab1.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class UserDTO {
    @NotNull
    private String username;
    @Email
    private String email;
    @NotNull
    private String password;
    private List<RatingDTO> ratings = new ArrayList<>();
    private List<PreferenceDTO> preferences = new ArrayList<>();
    private List<RecommendationDTO> recommendations = new ArrayList<>();
}
