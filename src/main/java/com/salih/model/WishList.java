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
@Table(name = "wish_lists")
public class WishList extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Kullanıcı User
    private User user;

    @ManyToMany
    @JoinTable(
            name = "wish_list_products",
            joinColumns = @JoinColumn(name = "wish_list_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
}
