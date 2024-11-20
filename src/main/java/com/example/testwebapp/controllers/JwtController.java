package com.example.testwebapp.controllers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testwebapp.common.ResponseObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("jwt")
public class JwtController {

    private SecretKey getSecretKey() {
        String secret = "This is my password 1234567qwerty@";
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return secretKey;
    }

    @GetMapping("/generate")
    public String generate(@RequestParam String subject) {
        LocalDateTime now = LocalDateTime.now();
        Date expiryTime = Date.from(now.plusDays(1).toInstant(ZoneOffset.UTC));

        return Jwts.builder().subject(subject).issuedAt(Date.from(now.toInstant(ZoneOffset.UTC))).expiration(expiryTime)
                .signWith(getSecretKey()).compact();
    }

    @GetMapping("/verify")
    public ResponseEntity<ResponseObject<Map<String, String>>> verify(@RequestParam String token) {
        boolean isValid = false;
        String subject = "";

        try {
            Claims payload = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();

            subject = payload.getSubject();
            isValid = true;
        } catch (Exception e) {

        }

        Map<String, String> profile = new HashMap<>();
        profile.put("subject", subject);

        return ResponseEntity.ok(new ResponseObject<Map<String, String>>(isValid, profile));
    }

    @GetMapping("/base64/encode")
    public String encodeText(@RequestParam String text) {
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("/base64/decode")
    public String decodeText(@RequestParam String text) {
        return new String(Base64.getDecoder().decode(text));
    }

    @GetMapping("/base64/urlencode")
    public String urlencode(@RequestParam String url) {
        return Base64.getUrlEncoder().encodeToString(url.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("/base64/urldecode")
    public String urldecode(@RequestParam String url) {
        return new String(Base64.getUrlDecoder().decode(url));
    }
}
