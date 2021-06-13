package com.kamronbek.auth.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {
    @NotNull(message = "FirstName is required")
    private final String firstName;

    @NotNull(message = "LastName is required")
    private final String lastName;

    @NotNull(message = "Email is required")
    @Email(message = "Email must be a valid email")
    private final String email;

    @NotNull(message = "Password is required")
    @Size(min = 8, max = 32, message = "The password must be between 8 and 32 characters long")
    private final String password;
}
