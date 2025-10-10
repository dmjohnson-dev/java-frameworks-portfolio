package com.example.demo.service;

import com.example.demo.domain.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override public List<Product> findAll() { return productRepository.findAll(); }

    @Override public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override public Product save(Product product) { return productRepository.save(product); }

    @Override public void deleteById(Long id) { productRepository.deleteById(id); }

    @Override public List<Product> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) return productRepository.findAll();
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
}

