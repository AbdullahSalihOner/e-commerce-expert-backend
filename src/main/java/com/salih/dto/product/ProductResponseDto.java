package com.salih.dto.product;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String categoryName;
    private String userName;  // User adını kullanıyoruz
    private List<String> images;
}