package com.salih.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private Boolean paymentStatus;

    public enum PaymentMethod {
        CREDIT_CARD, PAYPAL, BANK_TRANSFER
    }
}
