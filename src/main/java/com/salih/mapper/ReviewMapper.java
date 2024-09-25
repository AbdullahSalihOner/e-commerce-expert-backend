package com.salih.mapper;

import com.salih.dto.review.ReviewRequestDto;
import com.salih.dto.review.ReviewResponseDto;
import com.salih.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "buyer.id", target = "buyerId")
    Review toEntity(ReviewRequestDto reviewRequestDto);

    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "buyer.name", target = "buyerName")
    ReviewResponseDto toDto(Review review);
}