package com.example.lab1.controller;

import com.example.lab1.DTO.PreferenceDTO;
import com.example.lab1.mapper.PreferenceMapper;
import com.example.lab1.model.Preference;
import com.example.lab1.service.PreferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/preferences")
public class PreferenceController {
    private final PreferenceService preferenceService;
    private final PreferenceMapper preferenceMapper;

    public PreferenceController(PreferenceService preferenceService, PreferenceMapper preferenceMapper) {
        this.preferenceService = preferenceService;
        this.preferenceMapper = preferenceMapper;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPreferencesByUser(@PathVariable Long userId) {
        List<Preference> preferences = preferenceService.findByUser(userId);

        if (preferences.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Map<String, Object> response = preferenceMapper.toGroupedUserPreferences(preferences);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreferenceDTO> updatePreference(@PathVariable Long id, @RequestBody PreferenceDTO preferenceDTO) {
        Preference updatedPreference = preferenceService.updatePreference(id, preferenceDTO);
        return ResponseEntity.ok(preferenceMapper.toDTO(updatedPreference));
    }

    @PostMapping
    public ResponseEntity<PreferenceDTO> createPreference(@RequestBody PreferenceDTO preferenceDTO) {
        Preference createdPreference = preferenceService.createPreference(preferenceDTO);
        return new ResponseEntity<>(preferenceMapper.toDTO(createdPreference), HttpStatus.CREATED);
    }
}