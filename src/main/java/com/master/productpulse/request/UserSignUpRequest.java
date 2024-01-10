package com.master.productpulse.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignUpRequest {

    @NotNull(message = "Name is mandatory")
    @NotEmpty(message = "Name can't be empty")
    @Size(min = 3, max = 16, message
            = "Name must be between 3 and 16 characters")
    private String name;

    @NotNull(message = "Username is mandatory")
    @NotEmpty(message = "Username can't be empty")
    @Size(min = 3, max = 14, message
            = "Username must be between 3 and 16 characters")
    private String username;

//    @NotNull(message = "Password is mandatory")
//    @NotEmpty(message = "Password can't be empty")
//    @Size(min = 8, max = 8, message
//            = "Password must be 8 characters")
    private String password;

    @NotNull(message = "Phone is mandatory")
    @NotEmpty(message = "Phone can't be empty")
    @Size(min = 10, max = 10, message
            = "Phone number must be 10 digits")
    private String phone;

    @NotNull(message = "Email is mandatory")
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Email should be valid")
    private String email;

    private String userType;
}
