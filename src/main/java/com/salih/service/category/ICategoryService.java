package com.salih.service.category;

import com.salih.dto.category.CategoryRequestDto;
import com.salih.dto.category.CategoryResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;

import java.util.List;

public interface ICategoryService {
    DataResult<List<CategoryResponseDto>> getAllCategories();
    DataResult<CategoryResponseDto> getCategoryById(Long id);
    Result addCategory(CategoryRequestDto categoryRequestDto);
    Result updateCategory(Long id, CategoryRequestDto categoryRequestDto);
    Result deleteCategory(Long id);
}