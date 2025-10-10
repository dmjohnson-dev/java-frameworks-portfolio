package com.example.demo.repositories;

import com.example.demo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // supports search on the main screen
    List<Product> findByNameContainingIgnoreCase(String keyword);
}







