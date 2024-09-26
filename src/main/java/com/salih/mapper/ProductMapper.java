package com.salih.mapper;

import com.salih.dto.product.ProductRequestDto;
import com.salih.dto.product.ProductResponseDto;
import com.salih.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "userId", target = "user.id")  // DTO'daki userId'den Product entity'sindeki user.id'ye
    Product toEntity(ProductRequestDto productRequestDto);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "user.name", target = "userName")  // Entity'deki user.name'den DTO'daki userName'e
    ProductResponseDto toDto(Product product);
}
