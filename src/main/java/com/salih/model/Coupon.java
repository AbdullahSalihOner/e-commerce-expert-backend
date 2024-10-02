package com.salih.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "coupons")
public class Coupon extends BaseEntity implements Serializable {

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
