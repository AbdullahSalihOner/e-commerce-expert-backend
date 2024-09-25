package com.salih.dto.product;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer stockQuantity;

    private String categoryName;

    private String sellerName;

    private List<String> images;
}
