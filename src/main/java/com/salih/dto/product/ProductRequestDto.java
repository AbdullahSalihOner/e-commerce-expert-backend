package com.salih.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDto {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private Integer stockQuantity;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long sellerId;

    private List<String> images;
}
