package com.salih.service.category;

import com.salih.dto.category.CategoryRequestDto;
import com.salih.dto.category.CategoryResponseDto;
import com.salih.exception.ResourceNotFoundException;
import com.salih.mapper.CategoryMapper;
import com.salih.model.Category;
import com.salih.repository.CategoryRepository;
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
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Override
    public DataResult<List<CategoryResponseDto>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            logger.warn("No categories found");
            throw new ResourceNotFoundException("No categories found");
        }

        List<CategoryResponseDto> categoryDtos = categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());

        logger.info("Categories listed successfully");
        return new DataResult<>(categoryDtos, Result.showMessage(Result.SUCCESS, "Categories listed successfully"));
    }

    @Override
    public DataResult<CategoryResponseDto> getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", id);
                    return new ResourceNotFoundException("Category not found with ID: " + id);
                });

        CategoryResponseDto categoryDto = categoryMapper.toDto(category);
        logger.info("Category found with ID: {}", id);
        return new DataResult<>(categoryDto, Result.showMessage(Result.SUCCESS, "Category found"));
    }

    @Override
    public Result addCategory(CategoryRequestDto categoryRequestDto) {
        if (categoryRepository.existsByName(categoryRequestDto.getName())) {
            logger.warn("Category already exists with name: {}", categoryRequestDto.getName());
            return Result.showMessage(Result.FAILURE, "Category already exists with name: " + categoryRequestDto.getName());
        }
        Category category = categoryMapper.toEntity(categoryRequestDto);
        categoryRepository.save(category);
        logger.info("Category added successfully with ID: {}", category.getId());
        return Result.showMessage(Result.SUCCESS, "Category added successfully");
    }

    @Override
    public Result updateCategory(Long id, CategoryRequestDto categoryRequestDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (categoryRepository.existsByName(categoryRequestDto.getName())) {
            logger.warn("Category already exists with name: {}", categoryRequestDto.getName());
            return Result.showMessage(Result.FAILURE, "Category already exists with name: " + categoryRequestDto.getName());
        }

        existingCategory.setName(categoryRequestDto.getName());
        existingCategory.setDescription(categoryRequestDto.getDescription());

        categoryRepository.save(existingCategory);
        logger.info("Category updated successfully with ID: {}", id);
        return Result.SUCCESS;
    }

    @Override
    public Result deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        categoryRepository.delete(category);
        logger.info("Category deleted successfully with ID: {}", id);
        return Result.showMessage(Result.SUCCESS, "Category deleted successfully");
    }
}