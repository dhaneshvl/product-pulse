package com.master.productpulse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class VendorDTO {

    private int id;

    private String name;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a", locale = "en", timezone = "UTC")
    private LocalDateTime addedDate;

    private String proprietorName;

    private String address;

    private String gstNo;

    private int pincode;

    private String phone;

}
