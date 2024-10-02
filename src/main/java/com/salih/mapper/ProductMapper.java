package com.salih.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salih.dto.product.ProductRequestDto;
import com.salih.dto.product.ProductResponseDto;
import com.salih.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "images", target = "images", qualifiedByName = "mapImagesToEntity")  // images alanı JSON string olarak saklanacak
    Product toEntity(ProductRequestDto productRequestDto);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "images", target = "images", qualifiedByName = "mapImagesToDto")  // images alanı JSON'dan listeye çevrilecek
    ProductResponseDto toDto(Product product);

    @Named("mapImagesToEntity")
    default String mapImagesToEntity(List<String> images) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(images);  // Listeyi JSON formatına çeviriyoruz
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting images list to JSON", e);
        }
    }

    @Named("mapImagesToDto")
    default List<String> mapImagesToDto(String images) {
        if (images == null || images.isEmpty()) {
            return new ArrayList<>();  // Boş bir liste döndürün
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(images, List.class);  // JSON'dan listeye dönüştürüyoruz
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON string to images list", e);
        }
    }


}
