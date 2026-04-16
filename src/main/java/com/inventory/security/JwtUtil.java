package com.inventory.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // 🔐 Secret key (must be at least 32 characters)
    @Value("${jwt.secret:defaultsecretkeywhichmustbeatleast32chars}")
    private String secret = "mysecretkeymysecretkeymysecretkey";

    // ⏱ Token validity (1 hour)
    private static final long EXPIRATION = 1000 * 60 * 60;

    // 🔷 Generate signing key
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 🔷 Generate Token
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)   // ✅ new syntax (0.12.x)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey())   // ✅ no need for SignatureAlgorithm
                .compact();
    }

    // 🔷 Extract Username
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // 🔷 Validate Token
    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    // 🔷 Check expiration
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // 🔷 Extract all claims
    private Claims getClaims(String token) {
        return Jwts.parser()   // ✅ correct for 0.12.x
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}