package com.salih.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
public class Shipment extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "courier_id", nullable = false)
    private User courier; // Kargo kuryesi

    @Column(nullable = false)
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus shipmentStatus;

    @Column(nullable = false)
    private LocalDateTime estimatedDeliveryDate;

    public enum ShipmentStatus {
        PENDING, SHIPPED, DELIVERED
    }
}
