package com.salih.controller;

import com.salih.constant.ApiEndpoints;
import com.salih.dto.product.ProductRequestDto;
import com.salih.dto.product.ProductResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.product.IProductService;
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
@RequestMapping(ApiEndpoints.PRODUCT_BASE)
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "APIs for managing products")
public class ProductController {

    private final IProductService productService;

    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1))))
            .build();

    @Operation(summary = "Get all products", description = "Returns a list of all products.")
    @GetMapping(ApiEndpoints.PRODUCT_GET_ALL)
    public ResponseEntity<DataResult<List<ProductResponseDto>>> getAllProducts() {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        DataResult<List<ProductResponseDto>> result = productService.getAllProducts();
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @Operation(summary = "Get product by ID", description = "Returns a single product based on the provided ID.")
    @GetMapping(ApiEndpoints.PRODUCT_GET_BY_ID)
    public ResponseEntity<DataResult<ProductResponseDto>> getProductById(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        DataResult<ProductResponseDto> result = productService.getProductById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @Operation(summary = "Get products by category", description = "Returns a list of products in the provided category.")
    @GetMapping(ApiEndpoints.PRODUCT_GET_BY_CATEGORY)
    public ResponseEntity<DataResult<List<ProductResponseDto>>> getProductsByCategory(@PathVariable Long categoryId) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        DataResult<List<ProductResponseDto>> result = productService.getProductsByCategory(categoryId);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @Operation(summary = "Add new product", description = "Creates a new product with the provided data.")
    @PostMapping(ApiEndpoints.PRODUCT_ADD)
    public ResponseEntity<Result> addProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = productService.addProduct(productRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Update product", description = "Updates an existing product based on the provided ID and data.")
    @PutMapping(ApiEndpoints.PRODUCT_UPDATE)
    public ResponseEntity<Result> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto productRequestDto) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = productService.updateProduct(id, productRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Delete product", description = "Deletes an existing product based on the provided ID.")
    @DeleteMapping(ApiEndpoints.PRODUCT_DELETE)
    public ResponseEntity<Result> deleteProduct(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = productService.deleteProduct(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }
}
