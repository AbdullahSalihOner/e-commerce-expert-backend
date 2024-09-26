package com.salih.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor


@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Satıcı User
    private User user;

    @ElementCollection
    private List<String> images;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    @ManyToMany(mappedBy = "products")
    private List<WishList> wishLists;
}
