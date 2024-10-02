package com.salih.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.IOException;
import java.util.List;
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor

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



    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    @ManyToMany(mappedBy = "products")
    private List<WishList> wishLists;


    @Lob
    @Column(name = "images", columnDefinition = "TEXT")
    private String images;  // images alanını JSON string olarak saklayacağız

    // JSON string olarak kaydetmek için getter ve setter
    public List<String> getImagesAsList() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(this.images, List.class); // JSON'dan listeye dönüştürme
    }

    public void setImagesFromList(List<String> imagesList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.images = objectMapper.writeValueAsString(imagesList);  // Listeyi JSON string olarak kaydetme
    }

}
