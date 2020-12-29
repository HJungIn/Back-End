package com.project.gonggus.controller;

import com.project.gonggus.domain.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserContoller {
    @Autowired
    UserService userService;

    private final int EXPIRE_DAY = 3;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> loginExec(
            @RequestBody Map<String, Object> body, HttpServletResponse res
    ){
        Map<String, Object> resultMap = null;
        HttpStatus status = null;

        String userId = body.get("userId").toString();
        String userPassword = body.get("userPassword").toString();

        try {
            // token과 userData 정보를 프론트에 넘겨줌
            // 생성된 토큰을 Cookie에 저장하고 HttpOnly 설정
            resultMap = userService.login(userId, userPassword);
            res.addCookie(userService.setAuthCookie(resultMap.get("token").toString(), EXPIRE_DAY));
            // HTTP 상태 202 발신
            status = HttpStatus.ACCEPTED;
        } catch (RuntimeException e) {
            // 오류 메세지 출력 후 HTTP 상태 500 발신
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            System.out.println(e);
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> logoutExec(HttpServletResponse res){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.NO_CONTENT;
        // JWT 토큰과 같은 이름의 토큰을 만료된 시간으로 설정하여 저장.
        res.addCookie(userService.setAuthCookie(null, 0));

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/register")
    public void registerExec(@RequestBody Map<String, Object> body) {
        userService.register(body);
    }

    @PostMapping("/checklogin")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkLoginExec(HttpServletRequest res){
        Map<String, Object> resultMap = null;
        HttpStatus status = null;
        String token = null;
        try {
            Optional<Cookie> cookie = Arrays.stream(res.getCookies())
                    .filter(c -> c.getName().equals("auth_token"))
                    .findAny();
            if(cookie.isPresent()){
                token = cookie.get().getValue();
            }
            resultMap = userService.check(token);
            status = HttpStatus.ACCEPTED;
        } catch (RuntimeException e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            System.out.println(e);
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PutMapping("/mypage/modify")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateUserProfile(HttpServletRequest res,
                                                                 @RequestBody Map<String, Object> body){
        Map<String, Object> resultMap = null;
        HttpStatus status = null;

        String name = body.get("name").toString();
        String nickname = body.get("nickname").toString();

        String token = null;
        try {
            Optional<Cookie> cookie = Arrays.stream(res.getCookies())
                    .filter(c -> c.getName().equals("auth_token"))
                    .findAny();
            if (cookie.isPresent()) {
                token = cookie.get().getValue();
            }
            resultMap = userService.updateProfile(token, name, nickname);
            status = HttpStatus.ACCEPTED;
        } catch (RuntimeException e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            System.out.println(e);
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
