package com.salih.controller;

import com.salih.constant.ApiEndpoints;
import com.salih.dto.user.UserRequestDto;
import com.salih.dto.user.UserResponseDto;
import com.salih.dto.user.UserResponseWithBreadcrumbDto;
import com.salih.model.BreadcrumbItem;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.BreadcrumbService;
import com.salih.service.user.IUserService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping(ApiEndpoints.USER_BASE)
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "APIs for managing users")
public class UserController {

    private final IUserService userService;
    private final BreadcrumbService breadcrumbService;

    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1))))
            .build();

    @Operation(summary = "Get all users", description = "Returns a list of all users.")
    @GetMapping(ApiEndpoints.USER_GET_ALL)
    public ResponseEntity<UserResponseWithBreadcrumbDto> getAllUsers() {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }

        List<BreadcrumbItem> breadcrumbs = breadcrumbService.generateBreadcrumb("/users");

        DataResult<List<UserResponseDto>> result = userService.getAllUsers();
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(new UserResponseWithBreadcrumbDto(result.getData(), breadcrumbs));
    }

    @Operation(summary = "Get user by ID", description = "Returns a single user based on the provided ID.")
    @GetMapping(ApiEndpoints.USER_GET_BY_ID)
    public ResponseEntity<UserResponseWithBreadcrumbDto> getUserById(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }

        List<BreadcrumbItem> breadcrumbs = breadcrumbService.generateBreadcrumb("/users/" + id);

        DataResult<UserResponseDto> result = userService.getUserById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(new UserResponseWithBreadcrumbDto(List.of(result.getData()), breadcrumbs));
    }

    @Operation(summary = "Create new user", description = "Creates a new user with the provided data.")
    @PostMapping(ApiEndpoints.USER_CREATE)
    public ResponseEntity<Result> addUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = userService.addUser(userRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Update user", description = "Updates an existing user based on the provided ID and data.")
    @PutMapping(ApiEndpoints.USER_UPDATE)
    public ResponseEntity<Result> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDto userRequestDto) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = userService.updateUser(id, userRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Delete user", description = "Deletes a user based on the provided ID.")
    @DeleteMapping(ApiEndpoints.USER_DELETE)
    public ResponseEntity<Result> deleteUser(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = userService.deleteUser(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }
}
