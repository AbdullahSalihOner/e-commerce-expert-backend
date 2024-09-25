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
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "buyer")
    private List<Order> orders;

    @OneToMany(mappedBy = "seller")
    private List<Product> products;

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL)
    private About about;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WishList> wishLists;

    @OneToMany(mappedBy = "courier")
    private List<Shipment> shipments;

    public enum UserRole {
        BUYER, SELLER, ADMIN, CARGO_COURIER
    }
}
