package com.master.productpulse.exception;

public class ProductNotFoundException extends IllegalArgumentException {

    public ProductNotFoundException(String message) {
        super(message);
    }

}
