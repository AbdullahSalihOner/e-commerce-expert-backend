package com.salih.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.salih.dto.product.ProductRequestDto;
import com.salih.dto.product.ProductResponseDto;
import com.salih.exception.ResourceNotFoundException;
import com.salih.mapper.ProductMapper;
import com.salih.model.Category;
import com.salih.model.Product;
import com.salih.model.User;
import com.salih.repository.CategoryRepository;
import com.salih.repository.ProductRepository;
import com.salih.repository.UserRepository;
import com.salih.result.DataResult;
import com.salih.result.Result;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Override
    public DataResult<List<ProductResponseDto>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            logger.warn("No products found");
            throw new ResourceNotFoundException("No products found");
        }

        List<ProductResponseDto> productDtos = products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());

        logger.info("Products listed successfully");
        return new DataResult<>(productDtos, Result.showMessage(Result.SUCCESS, "Products listed successfully"));
    }

    @Cacheable(value = "products", key = "#id")  // Ürün Redis cache'den alınır
    @Override
    public DataResult<ProductResponseDto> getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", id);
                    return new ResourceNotFoundException("Product not found with ID: " + id);
                });

        ProductResponseDto productDto = productMapper.toDto(product);
        logger.info("Product found with ID: {}", id);
        return new DataResult<>(productDto, Result.showMessage(Result.SUCCESS, "Product found"));
    }

    @Override
    public DataResult<List<ProductResponseDto>> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            logger.warn("No products found for category ID: {}", categoryId);
            throw new ResourceNotFoundException("No products found for category ID: " + categoryId);
        }

        List<ProductResponseDto> productDtos = products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());

        logger.info("Products listed successfully for category ID: {}", categoryId);
        return new DataResult<>(productDtos, Result.showMessage(Result.SUCCESS, "Products listed successfully"));
    }

    @Override
    public Result addProduct(ProductRequestDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + productDto.getCategoryId()));

        User seller = userRepository.findById(productDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with ID: " + productDto.getUserId()));

        Product product = productMapper.toEntity(productDto);
        product.setCategory(category);
        product.setUser(seller);

        // Images alanını JSON olarak ayarlıyoruz
        try {
            product.setImagesFromList(productDto.getImages());
        } catch (JsonProcessingException e) {
            logger.error("Error converting images list to JSON", e);
            throw new RuntimeException("Error processing images list");
        }

        productRepository.save(product);
        logger.info("Product added successfully with ID: {}", product.getId());
        return Result.showMessage(Result.SUCCESS, "Product added successfully");
    }

    @Override
    public Result updateProduct(Long id, ProductRequestDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + productDto.getCategoryId()));

        User seller = userRepository.findById(productDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with ID: " + productDto.getUserId()));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setCategory(category);
        product.setUser(seller);

        // Images alanını JSON olarak ayarlıyoruz
        try {
            product.setImagesFromList(productDto.getImages());
        } catch (JsonProcessingException e) {
            logger.error("Error converting images list to JSON", e);
            throw new RuntimeException("Error processing images list");
        }

        productRepository.save(product);
        logger.info("Product updated successfully with ID: {}", id);
        return Result.showMessage(Result.SUCCESS, "Product updated successfully");
    }

    @CacheEvict(value = "products", key = "#id") // Ürün silindiğinde cache temizlenir
    @Override
    public Result deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        product.setIsDeleted(true);
        productRepository.save(product);
        logger.info("Product deleted successfully with ID: {}", id);
        return Result.showMessage(Result.SUCCESS, "Product deleted successfully");
    }
}
