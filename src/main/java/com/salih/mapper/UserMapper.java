package com.salih.mapper;

import com.salih.dto.user.UserRequestDto;
import com.salih.dto.user.UserResponseDto;
import com.salih.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDto userRequestDto);

    UserResponseDto toDto(User user);
}
