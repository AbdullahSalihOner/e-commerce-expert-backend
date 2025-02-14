package com.salih.dto.order;

import com.salih.model.Order;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
    private Long id;
    private String userName;  // Buyer User
    private List<String> productNames;
    private Double totalAmount;
    private LocalDateTime orderDate;
    private String status;
}