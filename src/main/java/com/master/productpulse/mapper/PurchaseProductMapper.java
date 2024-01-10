package com.master.productpulse.mapper;

import com.master.productpulse.model.PurchaseProduct;
import com.master.productpulse.request.PurchaseEntryProductFields;

import java.math.BigDecimal;

public class PurchaseProductMapper {

    public static PurchaseProduct mapToPurchaseProduct(PurchaseEntryProductFields entryProductFields) {
        PurchaseProduct purchaseProduct = new PurchaseProduct();
        purchaseProduct.setProductId(entryProductFields.getProductId());
        purchaseProduct.setQuantity(entryProductFields.getQuantity());
        purchaseProduct.setBasePrice(entryProductFields.getBasePrice());
        purchaseProduct.setDiscount(entryProductFields.getDiscount());

        double totalAmount = calculateTotalAmount(
                entryProductFields.getDiscount(),
                entryProductFields.getQuantity(),
                entryProductFields.getBasePrice()
        );

        purchaseProduct.setTotalAmount(totalAmount);

        return purchaseProduct;
    }
    // Method to calculate totalAmount
    public static double calculateTotalAmount(BigDecimal discount, Integer quantity, Double basePrice) {
        double basePriceDouble = basePrice;

        if (discount != null && discount.compareTo(BigDecimal.ZERO) > 0) {
            double discountPercentage = discount.doubleValue() / 100;
            double discountedPrice = basePriceDouble * (1 - discountPercentage);
            double totalAmount = discountedPrice * quantity;

            // Round to 2 decimal places
            return roundToTwoDecimalPlaces(totalAmount);
        } else {
            // Round to 2 decimal places
            return roundToTwoDecimalPlaces(basePriceDouble * quantity);
        }
    }

    // Helper method to round a double to two decimal places
    private static double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

}
