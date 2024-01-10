package com.master.productpulse.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VendorCreationRequest {

    @NotNull(message = "Store name cannot be null")
    @Size(min = 3, max = 16, message
            = "Store name must be between 3 and 16 characters")
    private String name;

    @NotNull(message = "Proprietor name name cannot be null")
    @Size(min = 3, max = 16, message
            = "Proprietor name must be between 3 and 16 characters")
    private String proprietorName;

    @NotNull(message = "Address cannot be null")
    @Size(min = 3, max = 64, message
            = "Address must be between 3 and 64 characters")
    private String address;

    @NotNull(message = "Pincode cannot be null")
    @Min(value = 600001, message = "Pincode should not be less than 600001")
    @Max(value = 643253, message = "Pincode should not be greater than 643253")
    private int pincode;

    @NotNull(message = "Phone cannot be null")
    @Size(min = 10, max = 10, message
            = "Phone number must be 10 digits")
    private String phone;

    @NotNull(message = "GST Number cannot be null")
    @NotEmpty(message = "GST Number cannot be empty")
    private String gstNo;
}
