package com.master.productpulse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SupplierDTO {

    private Long id;
    private String supplierName;
    private String address;
    private String gstNo;
    private String phone;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a", locale = "en", timezone = "UTC")
    private LocalDateTime addedDate;


}
