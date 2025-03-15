package com.example.lab1.mapper;

import com.example.lab1.DTO.GenreDTO;
import com.example.lab1.DTO.PreferenceDTO;
import com.example.lab1.model.Genre;
import com.example.lab1.model.Preference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = {GenreMapper.class})
public interface PreferenceMapper {

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "genres", source = "genre", qualifiedByName = "genreToGenreList")
    PreferenceDTO toDTO(Preference preference);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "genre", ignore = true)
    @Mapping(target = "preferenceId", ignore = true)
    Preference toEntity(PreferenceDTO preferenceDTO);

    List<PreferenceDTO> toDTOList(List<Preference> preferences);

    @Named("toGroupedUserPreferences")
    default Map<String, Object> toGroupedUserPreferences(List<Preference> preferences) {
        if (preferences == null || preferences.isEmpty()) {
            return Collections.emptyMap();
        }

        List<PreferenceDTO> dtoList = toDTOList(preferences);

        List<PreferenceDTO> cleanedDtos = dtoList.stream()
                .map(dto -> {
                    PreferenceDTO cleaned = new PreferenceDTO();
                    cleaned.setUsername(dto.getUsername());
                    cleaned.setGenres(dto.getGenres());
                    cleaned.setPreferredMaxDuration(dto.getPreferredMaxDuration());
                    cleaned.setPreferredMinYear(dto.getPreferredMinYear());
                    cleaned.setPreferredMaxYear(dto.getPreferredMaxYear());
                    cleaned.setPreferredMaxRating(dto.getPreferredMaxRating());
                    return cleaned;
                })
                .toList();
        return Map.of("preferences", cleanedDtos);
    }

    @Named("genreToGenreList")
    default List<GenreDTO> genreToGenreList(Genre genre) {
        if (genre == null) {
            return Collections.emptyList();
        }
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setName(genre.getName());
        return Collections.singletonList(genreDTO);
    }
}