package com.example.lab1.mapper;

import com.example.lab1.DTO.UserDTO;
import com.example.lab1.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RatingMapper.class, PreferenceMapper.class, RecommendationMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "ratings", source = "ratings")
    @Mapping(target = "preferences", source = "preferences")
    @Mapping(target = "recommendations", source = "recommendations")
    UserDTO toDTO(User user);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "preferences", ignore = true)
    @Mapping(target = "recommendations", ignore = true)
    User toEntity(UserDTO userDTO);

    List<UserDTO> toDTOList(List<User> users);
}