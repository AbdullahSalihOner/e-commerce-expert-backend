package com.salih.dto.review;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {

    private Long id;

    private String productName;

    private String buyerName;

    private Integer rating;

    private String comment;
}
