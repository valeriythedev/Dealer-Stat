package com.leverx.project.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthenticationRequest {

    private String email;
    private String password;
}
