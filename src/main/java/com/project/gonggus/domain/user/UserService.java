package com.project.gonggus.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(Map<String, String> param){
        User user = new User(param.get("name"),param.get("userId"),param.get("userPassword"),param.get("nickname"),param.get("schoolName"));
        userRepository.save(user);
    }


    public User getUser(String userId) {
        User user = userRepository.findByUserid(userId);
        return user;
    }
}
