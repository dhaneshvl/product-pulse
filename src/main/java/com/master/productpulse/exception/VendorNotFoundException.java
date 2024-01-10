package com.master.productpulse.exception;

public class VendorNotFoundException extends IllegalArgumentException {

    public VendorNotFoundException(String message) {
        super(message);
    }

}
