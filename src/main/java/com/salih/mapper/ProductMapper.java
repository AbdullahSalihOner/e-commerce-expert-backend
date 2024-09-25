package com.salih.mapper;

import com.salih.dto.product.ProductRequestDto;
import com.salih.dto.product.ProductResponseDto;
import com.salih.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "seller.id", target = "sellerId")
    Product toEntity(ProductRequestDto productRequestDto);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "seller.name", target = "sellerName")
    ProductResponseDto toDto(Product product);
}
