package com.example.lab1.controller;

import com.example.lab1.model.Preference;
import com.example.lab1.service.PreferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/preferences")
public class PreferenceController {
    private final PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Preference>> getPreferencesByUser(@PathVariable Long userId) {
        List<Preference> preferences = preferenceService.findByUser(userId);
        return preferences.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(preferences);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Preference> updateMovie(@PathVariable Long id, @RequestBody Preference preference) {
        return ResponseEntity.ok(preferenceService.updatePreference(id, preference));
    }

    @PostMapping
    public ResponseEntity<Preference> createPreference(@RequestBody Preference preference) {
        Preference createdPreference = preferenceService.createPreference(preference);
        return new ResponseEntity<>(createdPreference, HttpStatus.CREATED);
    }
}
