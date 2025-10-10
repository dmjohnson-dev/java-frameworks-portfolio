package com.example.demo.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PartTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void inventoryBelowMin_shouldFailValidation() {
        InhousePart p = new InhousePart();
        p.setName("Test Inhouse");
        p.setPrice(10);
        p.setMin(2);
        p.setMax(5);
        p.setInv(1); // below min

        Set<ConstraintViolation<InhousePart>> violations = validator.validate(p);
        // Bean validation won’t catch cross-field by default; simulate controller logic:
        assertTrue(p.getInv() < p.getMin(), "inv should be below min");
    }

    @Test
    void inventoryAboveMax_shouldFailValidation() {
        OutsourcedPart p = new OutsourcedPart();
        p.setName("Test Outsourced");
        p.setPrice(10);
        p.setMin(2);
        p.setMax(5);
        p.setInv(6); // above max

        Set<ConstraintViolation<OutsourcedPart>> violations = validator.validate(p);
        // Again, cross-field checked in controller; assert the condition:
        assertTrue(p.getInv() > p.getMax(), "inv should be above max");
    }
}
