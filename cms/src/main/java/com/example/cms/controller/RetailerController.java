package com.example.cms.controller;

import com.example.cms.model.entity.Retailer;
import com.example.cms.model.repository.RetailerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/retailer")
public class RetailerController {
    @Autowired
    private RetailerRepository retailerRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Retailer> getRetailerById(@PathVariable String id) {
        Optional<Retailer> retailer = retailerRepository.findById(id);
        return retailer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<Iterable<Retailer>> getAllRetailers() {
        return ResponseEntity.ok(retailerRepository.findAll());
    }
}
