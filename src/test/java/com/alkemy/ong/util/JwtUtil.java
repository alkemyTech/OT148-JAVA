package com.alkemy.ong.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtil {

    private final String SECRET = "secret";

    public String generateToken(String email, Long expiresIn) {
        return Jwts.builder().setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiresIn * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}