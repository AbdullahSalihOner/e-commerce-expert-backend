package com.salih.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDto implements Serializable {
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private Long categoryId;
    private Long userId;  // Seller için User ID'sini kullanıyoruz
    private List<String> images;
}