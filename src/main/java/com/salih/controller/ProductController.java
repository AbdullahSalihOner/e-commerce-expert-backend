package com.salih.controller;

import com.salih.dto.product.ProductRequestDto;
import com.salih.dto.product.ProductResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.product.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<DataResult<List<ProductResponseDto>>> getAllProducts() {
        DataResult<List<ProductResponseDto>> result = productService.getAllProducts();
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<ProductResponseDto>> getProductById(@PathVariable Long id) {
        DataResult<ProductResponseDto> result = productService.getProductById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<DataResult<List<ProductResponseDto>>> getProductsByCategory(@PathVariable Long categoryId) {
        DataResult<List<ProductResponseDto>> result = productService.getProductsByCategory(categoryId);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<Result> addProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        Result result = productService.addProduct(productRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto productRequestDto) {
        Result result = productService.updateProduct(id, productRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteProduct(@PathVariable Long id) {
        Result result = productService.deleteProduct(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }
}
