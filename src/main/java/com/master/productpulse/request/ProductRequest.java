package com.master.productpulse.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductRequest {

    private String productName;

    private Long supplierId;

    private Double price;

    private BigDecimal discount;

    private BigDecimal sgst;

    private BigDecimal cgst;

    private String hsnCode;
}
