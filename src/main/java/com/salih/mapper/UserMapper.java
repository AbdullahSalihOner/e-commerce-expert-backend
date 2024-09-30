package com.salih.mapper;

import com.salih.dto.user.UserRequestDto;
import com.salih.dto.user.UserResponseDto;
import com.salih.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", source = "role")  // Rolleri Liste olarak mapliyoruz
    User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "role", source = "role")  // DTO'daki rolleri entity'ye mapliyoruz
    UserResponseDto toDto(User user);
}