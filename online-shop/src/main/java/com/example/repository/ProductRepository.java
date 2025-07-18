package com.example.repository;

import com.example.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByProductName(String productName);

    List<Product> findAllByPrice(int price, Pageable pageable);


}
