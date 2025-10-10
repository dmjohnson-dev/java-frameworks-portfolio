package com.example.demo.service;

import com.example.demo.domain.Product;
import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Long id);

    Product save(Product product);

    void deleteById(Long id);

    /** Case-insensitive name search used by controllers */
    List<Product> searchByName(String keyword);
}

