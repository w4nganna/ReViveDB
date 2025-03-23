package com.example.cms.model.repository;

import com.example.cms.model.entity.Shopper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ShopperRepository extends JpaRepository<Shopper, String> {
}
