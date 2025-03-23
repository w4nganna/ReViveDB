package com.example.cms.controller;

import com.example.cms.controller.dto.ProductDto;
import com.example.cms.controller.exceptions.ProductNotFoundException;
import com.example.cms.controller.exceptions.CategoryNotFoundException;
import com.example.cms.model.entity.Category;
import com.example.cms.model.entity.Product;
import com.example.cms.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cms.model.repository.ProductRepository;


import java.util.stream.Collectors;

import java.util.List;

@RestController
@RequestMapping()
public class ProductController {
    @Autowired
    private final ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    //-----------------GET------------------
    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        ProductDto productDto = convertToDto(product);
        return ResponseEntity.ok(productDto);
    }
    // ---------- GET: Products by category ----------
    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        List<Product> products = repository.findByCategory(category);
        List<ProductDto> productDtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDtos);
    }

    private ProductDto convertToDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getBrand(),
                product.getOriginalPrice(),
                product.getNewPrice(),
                product.getCategory() != null ? product.getCategory().getCategoryId() : null,
                product.getRetailer() != null ? product.getRetailer().getId() : null,
                product.getImageURL(),
                product.getAverageScore()
        );
    }



}
