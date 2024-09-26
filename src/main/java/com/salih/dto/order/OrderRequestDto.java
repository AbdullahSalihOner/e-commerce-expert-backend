package com.salih.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {
    private Long userId;
    private List<Long> productIds;
    private Double totalAmount;
}