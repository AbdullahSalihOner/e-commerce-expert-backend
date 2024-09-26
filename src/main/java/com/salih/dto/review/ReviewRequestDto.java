package com.salih.dto.review;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ReviewRequestDto {
    private Long productId;
    private Long userId;  // buyer için User ID'sini kullanıyoruz
    private Integer rating;
    private String comment;
}