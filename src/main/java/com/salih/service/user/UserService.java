package com.salih.service.user;

import com.salih.dto.user.UserRequestDto;
import com.salih.dto.user.UserResponseDto;
import com.salih.exception.ResourceNotFoundException;
import com.salih.mapper.UserMapper;
import com.salih.model.User;
import com.salih.repository.UserRepository;
import com.salih.result.DataResult;
import com.salih.result.Result;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public DataResult<List<UserResponseDto>> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            logger.warn("No users found");
            throw new ResourceNotFoundException("No users found");
        }

        List<UserResponseDto> userDtos = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());

        logger.info("Users listed successfully");
        return new DataResult<>(userDtos, Result.showMessage(Result.SUCCESS, "Users listed successfully"));
    }

    @Override
    public DataResult<UserResponseDto> getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", id);
                    return new ResourceNotFoundException("User not found with ID: " + id);
                });

        UserResponseDto userDto = userMapper.toDto(user);
        logger.info("User found with ID: {}", id);
        return new DataResult<>(userDto, Result.showMessage(Result.SUCCESS, "User found"));
    }

    @Override
    public Result addUser(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        userRepository.save(user);
        logger.info("User added successfully with ID: {}", user.getId());
        return Result.showMessage(Result.SUCCESS, "User added successfully");
    }

    @Override
    public Result updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setRole(userRequestDto.getRole());

        userRepository.save(user);
        logger.info("User updated successfully with ID: {}", id);
        return Result.showMessage(Result.SUCCESS, "User updated successfully");
    }

    @Override
    public Result deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        userRepository.delete(user);
        logger.info("User deleted successfully with ID: {}", id);
        return Result.showMessage(Result.SUCCESS, "User deleted successfully");
    }
}
