package com.project.gonggus.domain.user;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final UserService userService;

    private String secretKey = "ThisisASecretKeyForJwt";

    public String makeJwt(Map<String, String> res) throws Exception {

        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("typ","JWT");
        headerMap.put("alg","HS256");

        Map<String, Object> map= new HashMap<String, Object>();
        String userId = res.get("userId");
        //String userPassword = res.get("userPassword");
        map.put("userId", userId);
        //map.put("userPassword", userPassword);

        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + 1000 * 60 * 60);//60분

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expireTime)
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    public boolean checkJwt(String jwt) throws Exception {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .parseClaimsJws(jwt).getBody();

            System.out.println("expireTime :" + claims.getExpiration());
            System.out.println("name :" + claims.get("name"));
            System.out.println("Email :" + claims.get("email"));

            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("토큰 만료");
            return false;
        } catch (JwtException exception) {
            System.out.println("토큰 변조");
            return false;
        }
    }

    public User getUserByJwt(String jwt) {

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(jwt).getBody();
        System.out.println("expireTime :" + claims.getExpiration());
        System.out.println("userId :" + claims.get("userId"));

        String userId = String.valueOf(claims.get("userId"));
        return userService.getUser(userId);
    }
}
