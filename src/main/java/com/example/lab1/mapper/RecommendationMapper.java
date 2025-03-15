package com.example.lab1.mapper;

import com.example.lab1.DTO.RecommendationDTO;
import com.example.lab1.model.Recommendation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "movie.title", target = "movieTitle")
    RecommendationDTO toDTO(Recommendation recommendation);

    @Mapping(target = "recommendationId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "movie", ignore = true)
    Recommendation toEntity(RecommendationDTO recommendationDTO);

    List<RecommendationDTO> toDTOList(List<Recommendation> recommendations);
}
