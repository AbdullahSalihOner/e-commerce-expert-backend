package com.salih.dto.review;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestDto {

    @NotNull
    private Long productId;

    @NotNull
    private Long buyerId;

    @NotNull
    private Integer rating;

    @NotNull
    private String comment;
}
