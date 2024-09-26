package com.salih.service.product;

import com.salih.dto.product.ProductRequestDto;
import com.salih.dto.product.ProductResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;

import java.util.List;

public interface IProductService {
    DataResult<List<ProductResponseDto>> getAllProducts();
    DataResult<ProductResponseDto> getProductById(Long id);
    DataResult<List<ProductResponseDto>> getProductsByCategory(Long categoryId);
    Result addProduct(ProductRequestDto productDto);
    Result updateProduct(Long id, ProductRequestDto productDto);
    Result deleteProduct(Long id);
}
