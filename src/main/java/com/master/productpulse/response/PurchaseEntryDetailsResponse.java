package com.master.productpulse.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEntryDetailsResponse {

    private Long purchaseEntryId;

    private String purchaseNote;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a", locale = "en", timezone = "UTC")
    private LocalDateTime purchaseDate;

    private String supplierName;

}
