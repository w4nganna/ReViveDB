package com.example.cms.model.repository;

import com.example.cms.model.entity.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RetailerRepository extends JpaRepository<Retailer, String>{
}
