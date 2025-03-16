package com.example.lab1.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
public class UserDTO {
    @NotNull
    private String username;
    @Email
    private String email;
    @NotNull
    private String password;
}
