package com.master.productpulse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.master.productpulse.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String productName;

    private Supplier supplier;

    private Double price;

    private BigDecimal discount;

    private BigDecimal sgst;

    private BigDecimal cgst;

    private String hsnCode;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a", locale = "en", timezone = "UTC")
    private LocalDateTime addedDate;
}
