package com.example.user_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserDto {

    private String fullName;
    private String email;
    private String password;
    private String role;
    private String address;

}
