package com.example.user_service.controller;

import com.example.user_service.dto.LoginReq;
import com.example.user_service.dto.ResponseDto;
import com.example.user_service.dto.UserDto;
import com.example.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody UserDto user) {
        return authService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginReq loginReq){
        return authService.login(loginReq);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String token){
        return authService.validateToken(token);
    }

}
