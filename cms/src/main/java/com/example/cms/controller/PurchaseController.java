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
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ShopperRepository shopperRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        return ResponseEntity.ok(purchaseRepository.findAll());
    }

    @GetMapping("/shopper/{shopperId}")
    public ResponseEntity<List<Purchase>> getPurchasesByShopper(@PathVariable String shopperId) {
        Shopper shopper = shopperRepository.findById(shopperId)
                .orElseThrow(() -> new RuntimeException("Shopper not found"));

        List<Purchase> purchases = purchaseRepository.findByShopper(shopper);
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
        return ResponseEntity.ok(purchase);
    }

    @PostMapping
    public ResponseEntity<Purchase> createPurchase(@RequestBody PurchaseRequestDto request) {
        Shopper shopper = shopperRepository.findById(request.getShopperId())
                .orElseThrow(() -> new RuntimeException("Shopper not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() < request.getQuantity()) {
            return ResponseEntity.badRequest().body(null); // or throw exception
        }

        product.setQuantity(product.getQuantity() - request.getQuantity());
        productRepository.save(product);

        Purchase purchase = new Purchase();
        purchase.setShopper(shopper);
        purchase.setProduct(product);
        purchase.setQuantity(request.getQuantity());
        purchase.setPurchaseDate(LocalDateTime.now().toString());
        purchase.setMoneySaved((product.getOriginalPrice() - product.getNewPrice()) * request.getQuantity());
        purchase.setCo2Saved(estimateCo2Saved(request.getQuantity()));

        purchaseRepository.save(purchase);
        return ResponseEntity.ok(purchase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePurchase(@PathVariable Long id, @RequestBody PurchaseRequestDto request) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        Product product = purchase.getProduct();
        int oldQty = purchase.getQuantity();
        int newQty = request.getQuantity();
        int diff = newQty - oldQty;

        if (product.getQuantity() < diff) {
            return ResponseEntity.badRequest().body("Not enough product in stock to update this purchase.");
        }

        // Update product quantity
        product.setQuantity(product.getQuantity() - diff);
        productRepository.save(product);

        // Update purchase
        purchase.setQuantity(newQty);
        purchase.setMoneySaved((product.getOriginalPrice() - product.getNewPrice()) * newQty);
        purchase.setCo2Saved(estimateCo2Saved(newQty));
        purchase.setPurchaseDate(LocalDateTime.now().toString());

        purchaseRepository.save(purchase);
        return ResponseEntity.ok(purchase);
    }

    private double estimateCo2Saved(int quantity) {
        return 0.113 * quantity; // Example CO2 saving
    }
}