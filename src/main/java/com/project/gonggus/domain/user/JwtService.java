package com.project.gonggus.domain.user;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtService {
    private String jwtSecret = "testkey";
    private Long expire = 7L;

    public String create (final User user) {
        final JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("type", "jwt")
                .setSubject("auth_token")
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expire))
                .claim("User", user)
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes());
        final String jwt = builder.compact();
        return jwt;
    }

    public Map<String, Object> get (final String jwt) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                        .setSigningKey(jwtSecret.getBytes())
                        .parseClaimsJws(jwt);
        } catch (final Exception e) {
            throw new RuntimeException();
        }

        return claims.getBody();
    }
}
