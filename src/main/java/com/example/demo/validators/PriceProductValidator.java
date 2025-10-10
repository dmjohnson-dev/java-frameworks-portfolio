package com.example.demo.validators;

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PriceProductValidator implements ConstraintValidator<ValidProductPrice, Product> {

    private final ProductService productService; // interface

    public PriceProductValidator(ProductService productService) {
        this.productService = productService;
    }

    @Override public void initialize(ValidProductPrice constraintAnnotation) {}

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext context) {
        if (product == null) return true;
        // Example: price must be >= 0
        return product.getPrice() >= 0;
    }
}

