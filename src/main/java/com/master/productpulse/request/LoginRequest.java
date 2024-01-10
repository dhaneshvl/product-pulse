package com.master.productpulse.request;

import com.master.productpulse.enums.UserType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginRequest {

    @NotNull(message = "Username is mandatory")
    @NotEmpty(message = "Username can't be empty")
    private String username;

    @NotNull(message = "Password is mandatory")
    @NotEmpty(message = "Password can't be empty")
    private String password;

    private UserType userType;
}
