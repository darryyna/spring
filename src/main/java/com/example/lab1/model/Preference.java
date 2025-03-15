package com.example.lab1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "preferences")
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preference_id")
    private Long preferenceId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @Column(name = "preferred_duration")
    private Integer preferredMaxDuration;

    @Column(name = "preferred_min_year")
    private Integer preferredMinYear;

    @Column(name = "preferred_max_year")
    private Integer preferredMaxYear;

    @Column(name = "preferred_max_rating")
    private Double preferredMaxRating;

}