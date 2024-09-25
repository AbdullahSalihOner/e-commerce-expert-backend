package com.salih.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "about_id", nullable = true)
    private About about;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String comment;
}
