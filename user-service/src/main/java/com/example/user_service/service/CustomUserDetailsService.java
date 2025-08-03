package com.example.user_service.service;

import com.example.user_service.model.CustomUserDetail;
import com.example.user_service.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(Boolean.FALSE.equals(userService.existsByEmail(email).getBody())){
            throw new UsernameNotFoundException("User not found");
        }
        UserPrincipal user = userService.getUserByEmail(email);

        return new CustomUserDetail(user);
    }
}
