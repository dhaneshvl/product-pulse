package com.master.productpulse.enums;

import lombok.Getter;

@Getter
public enum ProductType {
    LITER("Ltr"),
    PIECE("Pcs"),
    KG("Kg"),
    GM("G");

    private final String abbreviation;

    ProductType(String abbreviation) {
        this.abbreviation = abbreviation;
    }



}
