package com.salih.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {

    @NotNull
    private Long buyerId;

    @NotNull
    private List<Long> productIds;

    @NotNull
    private Double totalAmount;
}
