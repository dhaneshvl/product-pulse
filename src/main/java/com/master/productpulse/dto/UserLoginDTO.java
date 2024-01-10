package com.master.productpulse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginDTO {

    private Long id;

    private String name;

    private String username;

    private String email;

    private String phone;

    private String userType;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a", locale = "en", timezone = "UTC")
    private LocalDateTime addedDate;
}
