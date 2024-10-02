package com.salih.dto.shipment;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentRequestDto {
    private Long orderId;  // Sipariş ile ilişkilendirilecek
    private Long userId;    // Kargo kuryesi
    private String trackingNumber;
    private LocalDateTime estimatedDeliveryDate;
}