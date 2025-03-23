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
@Getter
@Setter
@NoArgsConstructor
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shopper_user_id")
    private Shopper shopper;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private Double co2Saved;

    private Double moneySaved;

    private String purchaseDate;

    public Purchase(Shopper shopper, Product product, int quantity) {
        this.shopper = shopper;
        this.product = product;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.moneySaved = (product.getOriginalPrice() - product.getNewPrice()) * quantity;
        this.co2Saved = estimateCo2Saved(product, quantity); // Example method
    }

    private Double estimateCo2Saved(Product product, int quantity) {
        return 0.113 * quantity; // 0.113 kg CO₂ saved per item
    }
}

