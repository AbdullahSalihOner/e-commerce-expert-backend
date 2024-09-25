package com.salih.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
public class Coupon extends BaseEntity {

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Double discountAmount;

    @Column(nullable = false)
    private LocalDateTime validUntil;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;
}
