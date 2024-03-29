package com.master.productpulse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseProductDTO {

    private Long productId;
    private Integer quantity;
    private String basePrice;
    private String discount;

}
