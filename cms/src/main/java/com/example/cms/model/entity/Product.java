package com.example.cms.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @NotNull
    // not sure if we need it
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private long productId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String brand;

    @NotNull
    private Double originalPrice;

    @NotNull
    private Double newPrice;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @NotEmpty
    private String imageURL;

    private Double averageScore;

    @ManyToOne
    @JoinColumn(name = "retailerId")
    private Retailer retailer;

    @NotNull
    private Integer quantity;

    public Product(long productId, String name, String brand, Double originalPrice, Double newPrice, Category category, String imageURL, Double averageScore, Integer quantity) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.originalPrice = originalPrice;
        this.newPrice = newPrice;
        this.category = category;
        this.imageURL = imageURL;
        this.averageScore = averageScore;
        this.quantity = quantity;
    }

}

