package com.project.gonggus.controller;

import com.project.gonggus.domain.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserContoller {
    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> loginExec(
            @RequestBody Map<String, Object> body, HttpServletResponse res
    ){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        String userId = body.get("userId").toString();
        String userPassword = body.get("userPassword").toString();

        try {
            // UserService.login()을 통하여 유저 정보를 받아와 토큰 생성
            User user = userService.login(userId, userPassword);
            String token = jwtService.create(user);
            // token과 userData 정보를 프론트에 넘겨줌
            resultMap.put("token", token);
            resultMap.put("userData", user);
            // 생성된 토큰을 Cookie에 저장하고 HttpOnly 설정
            Cookie cookie = new Cookie("auth_token", token);
            cookie.setHttpOnly(true);
            res.addCookie(cookie);
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
        Cookie cookie = new Cookie("auth_token", null);
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        resultMap.put("logout_ok", true);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/register")
    @ResponseBody
    public void registerExec(@RequestBody Map<String, Object> body) {
        String userId = body.get("userId").toString();
        String userPassword = body.get("userPassword").toString();
        String name = body.get("name").toString();
        String nickname = body.get("nickname").toString();
        String schoolName = body.get("schoolName").toString();
        User user = new User(name, userId, userPassword, nickname, schoolName);
        userService.register(user);
    }

    @PostMapping("/checklogin")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkLoginExec(@RequestHeader("Cookie") String cookie){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            String token = cookie.split("=")[1];
            resultMap.put("token", token);
            String userId = jwtService.get(token).get("userid").toString();
            User user = userService.getUser(userId);
            resultMap.put("userData", user);
            status = HttpStatus.ACCEPTED;
        } catch (RuntimeException e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            System.out.println(e);
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PutMapping("/mypage/{id}/modify")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateUserProfile(@PathVariable("id") String id,
                                                                 @RequestBody Map<String, Object> body){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        String name = body.get("name").toString();
        String nickname = body.get("nickname").toString();
        userService.updateUserProfile(id, name, nickname);

        User user = userService.getUser(id);
        resultMap.put("userData", user);
        status = HttpStatus.ACCEPTED;

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
