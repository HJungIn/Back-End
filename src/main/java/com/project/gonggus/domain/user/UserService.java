package com.project.gonggus.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(Map<String, String> param){
        if(param.isEmpty()){
            System.out.println("아무 정보도 없습니다.");
            return;
        }
        if(overlapCheck(param.get("userId"))){
            System.out.println("이미 존재하는 아이디 입니다.");
            return;
        }

        User user = new User(param.get("name"),param.get("userId"),param.get("userPassword"),param.get("nickname"),param.get("schoolName"));
        userRepository.save(user);
    }

    private boolean overlapCheck(String userId) {
        User user = getUser(userId);
        if(user!=null)
            return true;
        return false;
    }


    public User getUser(String userId) {
        User user = userRepository.findByUserId(userId);
        return user;
    }

    public void editUserInfo(Long userIdx, Map<String, String> param) {
        User user = getUserByIndex(userIdx);
        if(user==null){
            System.out.println("해당 유저가 없습니다");
            return;
        }

        user.setName(param.get("name"));
        user.setNickname(param.get("nickname"));
    }

    private User getUserByIndex(Long userIdx) {
        User user = userRepository.findByUserIdx(userIdx);
        return user;
    }
}
