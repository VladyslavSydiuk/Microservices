package com.example.repository;

import com.example.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByProductName(String productName);

    List<Product> findAllByPrice(int price, Pageable pageable);

    // Find products by category name with pagination
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    Page<Product> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

    // Find products by product name containing the search term
    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Product> findByProductNameContainingIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);
    
    // Find products by category name and product name containing the search term
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName AND LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Product> findByCategoryNameAndProductNameContainingIgnoreCase(@Param("categoryName") String categoryName, @Param("searchTerm") String searchTerm, Pageable pageable);

    // Find all products with pagination (when category is "all" or not specified)
    Page<Product> findAll(Pageable pageable);
}
