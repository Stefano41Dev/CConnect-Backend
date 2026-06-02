package com.stefano.web.dto.auth;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private LocalDate fechaNacimiento;
    private String password;
}
