package com.master.productpulse.exception;

public class DuplicateProductException extends IllegalArgumentException {


    public DuplicateProductException(String message) {
        super(message);
    }
}
