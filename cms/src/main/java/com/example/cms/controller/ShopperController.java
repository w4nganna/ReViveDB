package com.example.cms.controller;

import com.example.cms.controller.dto.ProductDto;
import com.example.cms.controller.exceptions.ProductNotFoundException;
import com.example.cms.controller.exceptions.ShopperNotFoundException;
import com.example.cms.model.entity.Product;
import com.example.cms.model.entity.Shopper;
import com.example.cms.model.repository.ProductRepository;
import com.example.cms.model.repository.ShopperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class ShopperController {
    private final ShopperRepository shopperRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ShopperController(ShopperRepository shopperRepository, ProductRepository productRepository) {
        this.shopperRepository = shopperRepository;
        this.productRepository = productRepository;
    }

    //Get favourite products
    @GetMapping("/shoppers/{shopperId}/favs")
    public Set<ProductDto> getFavProds(@PathVariable String shopperId) {
        Shopper shopper = shopperRepository.findById(shopperId)
                .orElseThrow(() -> new ShopperNotFoundException(shopperId));

        return shopper.getFavourites()
                .stream()
                .map(product -> new ProductDto(
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
                )) .collect(Collectors.toSet());
    }

    @PutMapping("/shoppers/{shopperId}/favs/{productId}")
    public ResponseEntity<String> updateFavProds(@PathVariable("shopperId") String shopperId,
                                                 @PathVariable("productId") Long productId) {

        // Find shopper
        Shopper shopper = shopperRepository.findById(shopperId)
                .orElseThrow(() -> new ShopperNotFoundException(shopperId));

        // Find product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        // Toggle favorite
        if (shopper.getFavourites().contains(product)) {
            // Remove favorite
            shopper.getFavourites().remove(product);
            shopperRepository.save(shopper);

            return ResponseEntity.ok("Product removed from favourites.");
        } else {
            // Add favorite
            shopper.getFavourites().add(product);
            shopperRepository.save(shopper);

            return ResponseEntity.ok("Product added to favourites.");
        }
    }

}
