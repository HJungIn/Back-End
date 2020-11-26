package com.project.gonggus.domain.user;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // 비밀번호 SHA-256으로 암호화
    private String encryptString (String input){
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            sha.update(input.getBytes());
            byte byteData[] = sha.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Boolean compareString (String a, String b){
        return a.equals(b);
    }

    // 회원가입
    public Boolean register (User user) {
        String id = user.getUserId();
        String password = null, encryptedPassword = null;
        if (userRepository.findByUserId(id) != null) {
            return false;
        }
        // 비밀번호 암호화 후 파라미터로 받아온 유저 객체에 저장
        try {
            password = user.getUserPassword();
            encryptedPassword = encryptString(password);
            user.setUserPassword(encryptedPassword);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }

        // 리포지토리를 통하여 데이터베이스에 저장
        userRepository.save(user);
        return true;
    }

    // 로그인
    public User login (String id, String password) {
        // 입력된 아이디, 비밀번호 길이 검사
        if(id.length() <= 0 || password.length() <= 0) {
            return null;
        }

        // 유저 데이터 요청
        User user = null;
        String inputPassword = null, userPassword = null;
        try {
            user = userRepository.findByUserId(id);
            inputPassword = encryptString(password);
            userPassword = user.getUserPassword();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }

        // 사용자 입력 비밀번호 인코딩 후 비밀번호 대조
        if(!compareString(inputPassword, userPassword)) {
            return null;
        }

        return user;
    }

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);

    }

    public UserDto getUserDto(String userId) {
        User user = userRepository.findByUserId(userId);
        return UserDto.convert(user);
    }
}
