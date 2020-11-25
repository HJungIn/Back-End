package com.project.gonggus.domain.user;

import io.jsonwebtoken.*;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private final String secretKey = "FE658C89D505800FC245C76AE8F0E3AACBE51191841E76A63011D1FE5AE5A3D7";

    private final Long expire = 7L;

    public String create(final User user) {
        final JwtBuilder builder = Jwts.builder();
        builder.setSubject("access_token")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * expire))
                .claim("user_id", user.getUserId())
                .claim("user_password", user.getUserPassword())
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes());

        final String jwt = builder.compact();
        return jwt;
    }

    public Map<String, Object> get(final String jwt) {
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(jwt);
        } catch (final Exception e) {
            throw new RuntimeException();
        }
        return claims.getBody();
    }
}
