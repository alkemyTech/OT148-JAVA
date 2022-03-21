package com.alkemy.ong.security;
/*
genera el token y tiene un metodo de validacion para ver requisitos formales
 */

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    private final String secret;
    private final int expiration;

    public JwtProvider(String secret, int expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(Authentication authentication) {
        MainUser mainUser = (MainUser) authentication.getPrincipal();

        return Jwts.builder().setSubject(mainUser.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("bad formed token");
        } catch (UnsupportedJwtException e) {
            logger.error("not supported token");
        } catch (ExpiredJwtException e) {
            logger.error("expired token");
        } catch (IllegalArgumentException e) {
            logger.error("empty token");
        } catch (SignatureException e) {
            logger.error("signature fail token ");
        }
        return false;
    }
}
