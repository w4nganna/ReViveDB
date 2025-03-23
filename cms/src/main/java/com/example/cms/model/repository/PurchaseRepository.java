package com.example.cms.model.repository;

import com.example.cms.model.entity.Purchase;
import com.example.cms.model.entity.Shopper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByShopper(Shopper shopper);
}