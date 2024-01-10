package com.master.productpulse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEntryDTO {

    private Long id;
    private Long supplierId;
    private String purchaseNote;
    private LocalDateTime purchaseDate;
    private List<PurchaseProductDTO> products;
    private String supplierName;

    public PurchaseEntryDTO(Long supplierId, String purchaseNote, LocalDateTime purchaseDate, List<PurchaseProductDTO> products) {
        this.supplierId = supplierId;
        this.purchaseNote = purchaseNote;
        this.purchaseDate = purchaseDate;
        this.products = products;
    }
}
