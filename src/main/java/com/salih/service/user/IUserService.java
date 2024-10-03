package com.salih.service.user;

import com.salih.dto.user.LoginDto;
import com.salih.dto.user.UserRequestDto;
import com.salih.dto.user.UserResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;

import java.util.List;

public interface IUserService {
    DataResult<List<UserResponseDto>> getAllUsers();
    DataResult<UserResponseDto> getUserById(Long id);
    Result addUser(UserRequestDto userRequestDto);
    Result updateUser(Long id, UserRequestDto userRequestDto);
    Result deleteUser(Long id);

    Result login(LoginDto loginDto);
    Result logout();
    Result signup(UserRequestDto userRequestDto);
}
