package com.master.productpulse.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseEntryProductFields {
    private Long productId;
    private Integer quantity;
    private BigDecimal discount;
    private Double basePrice;
}
