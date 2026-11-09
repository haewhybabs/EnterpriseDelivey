package com.parceldelivery.core.security;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    // Turns the raw secret string into a proper cryptographic key object the signing algorithm can use.
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Builds a new signed JWT for this email, stamped with when it was issued and when it expires.
    public String generateToken(String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .subject(email).issuedAt(now)
                .expiration(expiry)
                .signWith(getSigningKey())
                .compact();
    }

    // Pulls the email back out of a token's claims, without checking whether the token is still valid.
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // Confirms a token genuinely belongs to this email AND hasn't expired; false on any failure (bad signature, expired, malformed).
    public boolean isTokenValid(String token, String email) {
        try {
            Claims claims = extractClaims(token);
            return claims.getSubject().equals(email) && claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // Verifies the token's signature against our key and decodes its claims; throws if the token was tampered with or forged.
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
