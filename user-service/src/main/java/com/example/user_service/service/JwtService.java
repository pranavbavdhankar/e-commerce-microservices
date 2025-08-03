package com.example.user_service.service;

import com.example.user_service.model.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    private final String SECRET = System.getenv("SECRET");

    public String generateToken(UserPrincipal userPrincipal) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", userPrincipal.getRole());
        return createToken(userPrincipal.getEmail(), claims);
    }

    private String createToken(String email,  HashMap<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setExpiration(new Date( System.currentTimeMillis() +  24*60*60*1000))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, UserPrincipal userPrincipal) {
        String username = extractUsername(token);
        return username.equals(userPrincipal.getEmail());
    }

    public boolean isTokenExpired(String token) {

        Date expiretDate = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiretDate.before(new Date());

    }

    private Key getSignKey(){
        byte[] secret =  SECRET.getBytes();
        return Keys.hmacShaKeyFor(secret);
    }

}
