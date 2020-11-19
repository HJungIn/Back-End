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

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginExec(String id, String password, HttpServletResponse res){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            User user = userService.login(id, password);
            String token = jwtService.create(user);
            res.setHeader("jwt_auth_token", token);
            resultMap.put("status", true);
            resultMap.put("data", user);
            status = HttpStatus.ACCEPTED;
        } catch (RuntimeException e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/register")
    public String registerView() {
        return "register";
    }

    @PostMapping("/register")
    public String registerExec(String name, String userId, String userPassword, String nickname, String schoolName) {
        User user = new User(name, userId, userPassword, nickname, schoolName);
        return userService.register(user) ? "redirect:/login" : "register";
    }
}
