package com.salih.dto.shipment;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentResponseDto {
    private Long id;
    private String trackingNumber;
    private String shipmentStatus;
    private LocalDateTime estimatedDeliveryDate;
}