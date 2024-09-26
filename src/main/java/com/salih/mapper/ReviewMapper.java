package com.salih.mapper;

import com.salih.dto.review.ReviewRequestDto;
import com.salih.dto.review.ReviewResponseDto;
import com.salih.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "userId", target = "user.id")  // DTO'daki userId'den Review entity'sindeki user.id'ye
    Review toEntity(ReviewRequestDto reviewRequestDto);

    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "user.name", target = "userName")  // Entity'deki user.name'den DTO'daki userName'e
    ReviewResponseDto toDto(Review review);
}
