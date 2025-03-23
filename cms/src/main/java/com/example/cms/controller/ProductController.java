package com.example.cms.controller;

import com.example.cms.controller.dto.ProductDto;
import com.example.cms.controller.exceptions.ProductNotFoundException;
import com.example.cms.controller.exceptions.CategoryNotFoundException;
import com.example.cms.model.entity.Category;
import com.example.cms.model.entity.Product;
import com.example.cms.model.entity.Retailer;
import com.example.cms.model.repository.CategoryRepository;
import com.example.cms.model.repository.RetailerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private RetailerRepository retailerRepository;

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
    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody ProductDto dto) {
        // Find retailer
        Retailer retailer = retailerRepository.findById(dto.getRetailerId())
                .orElseThrow(() -> new RuntimeException("Retailer not found"));

        // Find category
        Category category = categoryRepository.findById(dto.getCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setNewPrice(dto.getNewPrice());
        product.setCategory(category);
        product.setRetailer(retailer);
        product.setImageURL(dto.getImageURL());
        product.setAverageScore(dto.getAverageScore());
        product.setQuantity(10); // or dto.getQuantity() if you add it to ProductDto

        repository.save(product);

        return ResponseEntity.ok("Product created successfully");
    }
    @GetMapping("/retailers/{retailerId}/products")
    public ResponseEntity<List<ProductDto>> getProductsByRetailer(@PathVariable String retailerId) {
        // Check retailer exists
        Retailer retailer = retailerRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));

        List<Product> products = repository.findByRetailer(retailer);

        List<ProductDto> productDtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDtos);
    }
    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody ProductDto dto) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        // Optional: Validate that the retailer owns this product
        if (!product.getRetailer().getId().equals(dto.getRetailerId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to update this product.");
        }

        // Update fields
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setNewPrice(dto.getNewPrice());
        product.setImageURL(dto.getImageURL());
        product.setAverageScore(dto.getAverageScore());
        product.setQuantity(dto.getQuantity());

        // Update category if needed
        if (dto.getCategory() != null) {
            Category category = categoryRepository.findById(dto.getCategory())
                    .orElseThrow(() -> new CategoryNotFoundException(dto.getCategory()));
            product.setCategory(category);
        }

        repository.save(product);
        return ResponseEntity.ok("Product updated successfully.");
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
                product.getAverageScore(),
                product.getQuantity()
        );
    }



}
