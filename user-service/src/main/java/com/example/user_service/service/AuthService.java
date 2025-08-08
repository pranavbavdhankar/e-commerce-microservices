package com.example.user_service.service;

import com.example.user_service.dto.LoginReq;
import com.example.user_service.dto.ResponseDto;
import com.example.user_service.dto.UserDto;
import com.example.user_service.model.CustomUserDetail;
import com.example.user_service.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authManager;
    public ResponseEntity<ResponseDto> registerUser(UserDto user) {

        if(Boolean.FALSE.equals(userService.existsByEmail(user.getEmail()).getBody())){
            UserPrincipal newUser = new UserPrincipal(user);
            newUser.setUserId(UUID.randomUUID().toString());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setActive(true);

            UserPrincipal savedUser = userService.registerUser(newUser);
            if(savedUser != null){
                return ResponseEntity.ok(
                        new ResponseDto("User Register Sucessfully", HttpStatus.OK.value(), LocalDateTime.now())
                );
            }else{
                return new ResponseEntity<>(
                        new ResponseDto("Failed to register user, Try again later!", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR
                );
            }

        }
        return new ResponseEntity<>(
                new ResponseDto("User already exist", HttpStatus.OK.value(), LocalDateTime.now()), HttpStatus.OK
        );
    }

    public ResponseEntity<String> login(LoginReq loginReq) {

        try{
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword())
            );
            CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
            return ResponseEntity.ok(jwtService.generateToken(user.getUserPrincipal()));
        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid email or password");
        }

    }

    public ResponseEntity<Boolean> validateToken(String token) {
        if(!jwtService.isTokenExpired(token)){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }
}
