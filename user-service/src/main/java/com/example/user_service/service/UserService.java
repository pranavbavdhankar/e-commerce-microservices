package com.example.user_service.service;

import com.example.user_service.dto.UserDto;
import com.example.user_service.model.UserPrincipal;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public ResponseEntity<Boolean> existsByEmail(String email) {
        return ResponseEntity.ok(userRepository.existsByEmail(email));
    }

    public UserPrincipal registerUser(UserPrincipal newUser) {
        return userRepository.save(newUser);
    }

    public UserPrincipal getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }
}
