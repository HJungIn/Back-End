package com.project.gonggus.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void saveUser(User user){userRepository.save(user);}


    public User getUser(String userId) {
        User user = userRepository.findByUserid(userId);
        return user;
    }
}
