package com.example.mvc2.validation;

import com.example.mvc2.domain.item.Item;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

class BeanValidationTest {
    private static Validator validator;
    private Set<ConstraintViolation<Item>> constraintViolations;

    @BeforeAll
    static void setUp() {
        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void beanValidation() {
        final Item item = new Item("", 100, 10000);
        constraintViolations = validator.validate(item);

        for (ConstraintViolation<Item> constraintViolation : constraintViolations) {
            System.out.println("constraintViolation = " + constraintViolation);
            System.out.println("constraintViolation.getMessage() = " + constraintViolation.getMessage());
        }
    }
}
