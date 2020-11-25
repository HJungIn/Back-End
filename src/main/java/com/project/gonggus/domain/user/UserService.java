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

    public Boolean register (User user) {
        String password = user.getUserPassword();
        if (password == null) {
            return false;
        }
        String encryptedPassword = encryptString(password);
        if (encryptedPassword == null) {
            return false;
        }
        user.setUserPassword(encryptedPassword);
        userRepository.save(user);
        return true;
    }

    public User login (String id, String password) {
//        // 입력된 아이디, 비밀번호 길이 검사
//        if(id.length() <= 0 || password.length() <= 0) {
//            return null;
//        }

        // 유저 데이터 요청, 존재하지 않을 시 false 반환
        User user = userRepository.findByUserId(id);
//        if(user == null) {
//            return null;
//        }

        // 유저 비밀번호 대조하고 불일치하면 false 반환
        String inputPassword = encryptString(password);
        String userPassword = user.getUserPassword();
//        if(!compareString(inputPassword, userPassword)) {
//            return null;
//        }

        return user;
    }
}
