package com.alkemy.ong.util;

import org.springframework.http.HttpHeaders;

public class HeaderBuilder {
    
    private final HttpHeaders headers = new HttpHeaders();

    public HttpHeaders build() {
        return headers;
    }

    public HeaderBuilder withValidToken(String email, Long expiresIn) {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken(email, expiresIn);
        headers.add("Authorization", "Bearer " + token);
        return this;
    }
}
