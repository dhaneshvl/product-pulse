package com.master.productpulse.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

public class ValidationUtils {

    public static String extractValidationErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }
}
