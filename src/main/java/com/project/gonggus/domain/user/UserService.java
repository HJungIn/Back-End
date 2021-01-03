package com.project.gonggus.domain.user;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    public void join (User user) {
        String userPassword = user.getUserPassword();
        user.setUserPassword(authService.encryptString(userPassword));
        userRepository.save(user);
    }

    public void save (User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserByUserId (String userId) {
       return Optional.ofNullable(userRepository.findByUserId(userId));
    }

    public User getUserByCookie(Cookie cookie) {
        String userId = authService.get(cookie.getValue()).get("userId").toString();
        return userRepository.findByUserId(userId);
    }
}
