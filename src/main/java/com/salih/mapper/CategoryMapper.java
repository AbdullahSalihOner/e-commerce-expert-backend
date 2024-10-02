package com.salih.mapper;

import com.salih.dto.category.CategoryRequestDto;
import com.salih.dto.category.CategoryResponseDto;
import com.salih.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);
    Category toEntity(CategoryRequestDto categoryRequestDto);
}