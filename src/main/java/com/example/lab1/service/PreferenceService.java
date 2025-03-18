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
import com.example.lab1.service.customException.DuplicateResourceException;
import com.example.lab1.service.customException.ResourceNotFoundException;
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

    public Preference findPreferenceById(Long preferenceId) throws ResourceNotFoundException {
        return preferenceRepository.findById(preferenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Preference with id " + preferenceId + " not found"));
    }

    public List<Preference> findByUser(Long userId) throws ResourceNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        }
        return preferenceRepository.findByUser_UserId(userId);
    }

    public Preference createPreference(PreferenceDTO preferenceDTO) throws DuplicateResourceException {
        String username = preferenceDTO.getUsername();
        Optional<Preference> existingUserPreference = preferenceRepository.findByUserUsername(username);

        if (existingUserPreference.isPresent()) {
            throw new DuplicateResourceException("Preferences for user '" + username + "' already exist");
        }
        Preference preference = preferenceMapper.toEntity(preferenceDTO);
        return getPreference(preferenceDTO, preference);
    }

    public Preference updatePreference(Long preferenceId, Preference preference) throws ResourceNotFoundException {
        Preference existingPreference = findPreferenceById(preferenceId); // використовуємо метод для перевірки преференса
        preference.setPreferenceId(existingPreference.getPreferenceId());
        return preferenceRepository.save(preference);
    }

    private Preference getPreference(PreferenceDTO preferenceDTO, Preference updatedPreference) {
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
    }
}