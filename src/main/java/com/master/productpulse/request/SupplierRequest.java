package com.master.productpulse.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SupplierRequest {

    @NotNull(message = "Supplier name cannot be null")
    @NotEmpty(message = "Supplier name cannot be empty")
    @Size(min = 3, max = 16, message
            = "Supplier name must be between 3 and 16 characters")
    private String supplierName;

    @NotNull(message = "Address cannot be null")
    @NotEmpty(message = "Address cannot be empty")
    @Size(min = 3, max = 64, message
            = "Address must be between 3 and 16 characters")
    private String address;

    @NotNull(message = "GST NO cannot be null")
    @NotEmpty(message = "GST NO cannot be empty")
    @Size(min = 3, max = 16, message
            = "GST NO must be between 3 and 16 characters")
    private String gstNo;

    @NotNull(message = "Phone is mandatory")
    @NotEmpty(message = "Phone can't be empty")
    @Size(min = 10, max = 10, message
            = "Phone number must be 10 digits")
    private String phone;


}
