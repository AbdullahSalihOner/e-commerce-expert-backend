package com.salih.controller;

import com.salih.dto.user.UserRequestDto;
import com.salih.dto.user.UserResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/all")
    public ResponseEntity<DataResult<List<UserResponseDto>>> getAllUsers() {
        DataResult<List<UserResponseDto>> result = userService.getAllUsers();
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<UserResponseDto>> getUserById(@PathVariable Long id) {
        DataResult<UserResponseDto> result = userService.getUserById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<Result> addUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        Result result = userService.addUser(userRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDto userRequestDto) {
        Result result = userService.updateUser(id, userRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteUser(@PathVariable Long id) {
        Result result = userService.deleteUser(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }
}
