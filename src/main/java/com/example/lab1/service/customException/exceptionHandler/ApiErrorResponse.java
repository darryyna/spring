package com.example.lab1.service.customException.exceptionHandler;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
