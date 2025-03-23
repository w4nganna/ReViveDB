package com.example.cms.model.repository;

import com.example.cms.model.entity.Category;
import com.example.cms.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //------------Product search method I: Java filter-----------
    @Query(value = "SELECT * FROM products p " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'l2h' THEN p.price END ASC, " +
            "CASE WHEN :sortBy = 'h2l' THEN p.price END DESC", nativeQuery = true)
    List<Product> findAllProductsSorted(@Param("sortBy") String sortBy);

    //------------Product search method II: SQL filter-----------
    @Query(value = "SELECT p.* FROM products p " +

            "LEFT JOIN ProductCategory pca ON p.productId = pca.productId " +
            "WHERE (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:brands IS NULL OR :brands = '' OR p.brand IN :brands) " +
            "AND (:category IS NULL OR :category = '' OR pca.categoryId = :category) " +
            "CASE WHEN :sortBy = 'price_low_to_high' THEN p.price END ASC, " +
            "CASE WHEN :sortBy = 'price_high_to_low' THEN p.price END DESC", nativeQuery = true)
    List<Product> findFilteredProducts(
            @Param("brands") List<String> brands,
            @Param("category") Integer category,
            @Param("sortBy") String sortBy
    );
    List<Product> findByCategory(Category category);
}
