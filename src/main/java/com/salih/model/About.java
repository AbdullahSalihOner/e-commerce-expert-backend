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
@Table(name = "about")
public class About extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User seller;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = true)
    private String historyOfSeller;

    @Column(nullable = true)
    private String processOfProduction;

    @OneToMany(mappedBy = "about", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
