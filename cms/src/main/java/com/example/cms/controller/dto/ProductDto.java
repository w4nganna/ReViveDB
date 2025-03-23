package com.example.cms.controller.dto;

import com.example.cms.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class ProductDto {
    private long id;
    private String name;
    private String brand;
    private Double originalPrice;
    private Double newPrice;
    private Long category;
    private String retailerId;
    private String imageURL;
    private Double averageScore;
    private Integer quantity;
}
