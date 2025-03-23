package com.example.cms.controller;

import com.example.cms.controller.dto.PurchaseRequestDto;
import com.example.cms.model.entity.Purchase;
import com.example.cms.model.entity.Product;
import com.example.cms.model.entity.Shopper;
import com.example.cms.model.repository.ProductRepository;
import com.example.cms.model.repository.PurchaseRepository;
import com.example.cms.model.repository.ShopperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ShopperRepository shopperRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Purchase> createPurchase(@RequestBody PurchaseRequestDto request) {
        Shopper shopper = shopperRepository.findById(request.getShopperId())
                .orElseThrow(() -> new RuntimeException("Shopper not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Purchase purchase = new Purchase();
        purchase.setShopper(shopper);
        purchase.setProduct(product);
        purchase.setQuantity(request.getQuantity());
        purchase.setPurchaseDate(java.time.LocalDateTime.now().toString());
        purchase.setMoneySaved((product.getOriginalPrice() - product.getNewPrice()) * request.getQuantity());
        purchase.setCo2Saved(estimateCo2Saved(request.getQuantity())); // Simple estimate

        purchaseRepository.save(purchase);
        return ResponseEntity.ok(purchase);
    }

    private double estimateCo2Saved(int quantity) {
        return 0.113 * quantity; // Example CO2 saving
    }
}