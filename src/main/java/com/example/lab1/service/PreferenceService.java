package com.example.lab1.service;

import com.example.lab1.DTO.GenreDTO;
import com.example.lab1.DTO.PreferenceDTO;
import com.example.lab1.mapper.PreferenceMapper;
import com.example.lab1.model.Genre;
import com.example.lab1.model.Preference;
import com.example.lab1.model.User;
import com.example.lab1.repository.GenreRepository;
import com.example.lab1.repository.PreferenceRepository;
import com.example.lab1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final PreferenceMapper preferenceMapper;

    public PreferenceService(
            PreferenceRepository preferenceRepository,
            UserRepository userRepository,
            GenreRepository genreRepository,
            PreferenceMapper preferenceMapper
    ) {
        this.preferenceRepository = preferenceRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.preferenceMapper = preferenceMapper;
    }

    public List<Preference> findByUser(Long userId) {
        return preferenceRepository.findByUser_UserId(userId);
    }

    public Preference createPreference(PreferenceDTO preferenceDTO) {
        Preference preference = preferenceMapper.toEntity(preferenceDTO);

        User user = userRepository.findByUsername(preferenceDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User with username " + preferenceDTO.getUsername() + " not found"));
        preference.setUser(user);
        if (preferenceDTO.getGenres() != null && !preferenceDTO.getGenres().isEmpty()) {
            GenreDTO genreDTO = preferenceDTO.getGenres().get(0);
            Genre genre = genreRepository.findByName(genreDTO.getName());
            if (genre == null) {
                throw new RuntimeException("Genre with name " + genreDTO.getName() + " not found");
            }
            preference.setGenre(genre);
        }

        return preferenceRepository.save(preference);
    }

    public Preference updatePreference(Long id, PreferenceDTO preferenceDTO) {
        Optional<Preference> existingPreferenceOpt = preferenceRepository.findById(id);
        if (existingPreferenceOpt.isPresent()) {
            Preference existingPreference = existingPreferenceOpt.get();
            Preference updatedPreference = preferenceMapper.toEntity(preferenceDTO);
            updatedPreference.setPreferenceId(id);

            User user = userRepository.findByUsername(preferenceDTO.getUsername())
                    .orElseThrow(() -> new RuntimeException("User with username " + preferenceDTO.getUsername() + " not found"));
            updatedPreference.setUser(user);

            if (preferenceDTO.getGenres() != null && !preferenceDTO.getGenres().isEmpty()) {
                GenreDTO genreDTO = preferenceDTO.getGenres().get(0);
                Genre genre = genreRepository.findByName(genreDTO.getName());
                if (genre == null) {
                    throw new RuntimeException("Genre with name " + genreDTO.getName() + " not found");
                }
                updatedPreference.setGenre(genre);
            }

            return preferenceRepository.save(updatedPreference);
        } else {
            throw new RuntimeException("Preference with id " + id + " not found");
        }
    }

    public Preference createPreference(Preference preference) {
        return preferenceRepository.save(preference);
    }
}