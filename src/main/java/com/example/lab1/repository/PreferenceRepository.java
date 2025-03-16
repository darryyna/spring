package com.example.lab1.repository;

import com.example.lab1.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    List<Preference> findByUser_UserId(Long userId);

    Optional<Preference> findByUserUsername(String username);
}


