package com.example.user_service.model;

import com.example.user_service.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserPrincipal {

    @Id
    private String userId;
    private String fullName;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String role;
    private String address;
    private boolean isActive;

    public UserPrincipal(UserDto user) {
        this.fullName =  user.getFullName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.address = user.getAddress();
    }
}
