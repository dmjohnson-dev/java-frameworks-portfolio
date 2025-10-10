package com.example.demo.validators;

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class EnufPartsValidator implements ConstraintValidator<ValidEnufParts, Product> {

    private final ProductService productService; // interface

    public EnufPartsValidator(ProductService productService) {
        this.productService = productService;
    }

    @Override public void initialize(ValidEnufParts constraintAnnotation) {}

    @Override
    public boolean isValid(Product value, ConstraintValidatorContext context) {
        if (value == null) return true;
        // TODO: put your real business rule here. Returning true avoids blocking saves.
        return true;
    }
}
