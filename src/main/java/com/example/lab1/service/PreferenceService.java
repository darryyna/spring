package com.example.lab1.service;

import com.example.lab1.model.Preference;
import com.example.lab1.repository.PreferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;

    public PreferenceService(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public List<Preference> findByUser(Long userId) {
        return preferenceRepository.findByUser_UserId(userId);
    }

    public Preference createPreference(Preference preference) {
        return preferenceRepository.save(preference);
    }

    public Preference updatePreference(Long id, Preference preference) {
        Optional<Preference> existingPreference = preferenceRepository.findById(id);
        if (existingPreference.isPresent()) {
            preference.setPreferenceId(id);
            return preferenceRepository.save(preference);
        } else {
            throw new RuntimeException("Preference with id " + id + " not found");
        }
    }
}