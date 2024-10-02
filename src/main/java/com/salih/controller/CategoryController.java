package com.salih.controller;

import com.salih.constant.ApiEndpoints;
import com.salih.dto.category.CategoryRequestDto;
import com.salih.dto.category.CategoryResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.category.ICategoryService;
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
@RequestMapping(ApiEndpoints.CATEGORY_BASE)
@RequiredArgsConstructor
@Tag(name = "Category Controller", description = "APIs for managing product categories")
public class CategoryController {

    private final ICategoryService categoryService;

    // Rate limiting: 100 requests per minute
    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1))))
            .build();

    @Operation(summary = "Get all categories", description = "Returns a list of all categories.")
    @GetMapping(ApiEndpoints.CATEGORY_GET_ALL)
    public ResponseEntity<DataResult<List<CategoryResponseDto>>> getAllCategories() {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        DataResult<List<CategoryResponseDto>> result = categoryService.getAllCategories();
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @Operation(summary = "Get category by ID", description = "Returns a category by the provided ID.")
    @GetMapping(ApiEndpoints.CATEGORY_GET_BY_ID)
    public ResponseEntity<DataResult<CategoryResponseDto>> getCategoryById(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        DataResult<CategoryResponseDto> result = categoryService.getCategoryById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @Operation(summary = "Create new category", description = "Creates a new product category.")
    @PostMapping(ApiEndpoints.CATEGORY_CREATE)
    public ResponseEntity<Result> addCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = categoryService.addCategory(categoryRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Update category", description = "Updates an existing category by ID.")
    @PutMapping(ApiEndpoints.CATEGORY_UPDATE)
    public ResponseEntity<Result> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = categoryService.updateCategory(id, categoryRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Delete category", description = "Deletes a category by ID.")
    @DeleteMapping(ApiEndpoints.CATEGORY_DELETE)
    public ResponseEntity<Result> deleteCategory(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = categoryService.deleteCategory(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }
}