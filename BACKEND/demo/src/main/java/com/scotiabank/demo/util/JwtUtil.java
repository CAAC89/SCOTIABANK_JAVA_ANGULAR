package com.scotiabank.demo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.util.Date;

import java.security.Key;
@Component

public class JwtUtil {
    private static final String SECRET_KEY = "mysupersecretkeyformytokens12345678901234567890"; // Debe tener al menos 32 bytes
    private static final Key SIGNING_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String email) {
        return Jwts.builder()
                   .setSubject(email)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora de validez
                   .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                   .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(SIGNING_KEY)
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(SIGNING_KEY)
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getExpiration();
    }

    public boolean validateToken(String token, String email) {
        return (email.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
