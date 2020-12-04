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
    private String issuer = "bss";
    private Long expire = 7L;

    // 토큰 생성
    public String create (final User user) {
        final JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setSubject("userid")
                .claim("id", user.getId())
                .claim("userId", user.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expire))
                .setIssuer(issuer)
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes());
        final String jwt = builder.compact();
        return jwt;
    }

    // 토큰 검증 후 정보 얻기
    public Map<String, Object> get (final String jwt) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                        .setSigningKey(jwtSecret.getBytes())
                        .requireIssuer(issuer)
                        .parseClaimsJws(jwt);
        } catch (final Exception e) {
            throw new RuntimeException();
        }

        return claims.getBody();
    }

    public Map<String, Object> getByCookie (final String cookie) {
        if (cookie.equals(null)) return null;
        String token = cookie.split("=")[1];
        return get(token);
    }
}
