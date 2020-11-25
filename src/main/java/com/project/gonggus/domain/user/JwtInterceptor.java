package com.project.gonggus.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        if (req.getMethod().equals("OPTION")) {
            return true;
        }

        String token = req.getHeader("jwt_auth_token");
        if (token != null && token.length() > 0) {
            return true;
        } else {
            throw new RuntimeException();
        }
    }
}
