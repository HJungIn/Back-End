package com.project.gonggus.controller;

import com.project.gonggus.domain.user.JwtService;
import com.project.gonggus.domain.user.User;
import com.project.gonggus.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/home")
    public String index(){
        return "home";
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginExec(@RequestBody Map<String, Object> body, HttpServletResponse res){
        String userId = body.get("userId").toString();
        String userPassword = body.get("userPassword").toString();
        System.out.println(userId + ", " + userPassword);
        HttpStatus status = null;
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User user = userService.login(userId, userPassword);
            String token = jwtService.create(user);
            resultMap.put("user", user);
            resultMap.put("token", token);
            res.addCookie(new Cookie("jwt_auth_token", token));
            status = HttpStatus.ACCEPTED;
        } catch (RuntimeException e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            System.out.println(e);
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

//    @GetMapping("/register")
//    public String registerView() {
//        return "register";
//    }

    @PostMapping("/register")
    public void registerExec(@RequestBody Map<String, Object> body) {
        String userId = body.get("userId").toString();
        String userPassword = body.get("userPassword").toString();
        String name = body.get("name").toString();
        String nickname = body.get("nickname").toString();
        String schoolName = body.get("schoolName").toString();
        User user = new User(name, userId, userPassword, nickname, schoolName);
        userService.register(user);
    }
}
