package com.salih.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private List<UserRole> role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore  // Serileştirme sırasında sonsuz döngüye girmemek için
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Product> products;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private About about;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WishList> wishLists;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Shipment> shipments;

    public enum UserRole {
        BUYER, SELLER, ADMIN, CARGO_COURIER
    }
}