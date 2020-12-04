package com.project.gonggus.domain.user;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    // 회원가입
    public Long register (User user) {
        String password, encryptedPassword = null;
        // 회원 중복 체크
        try {
            validateDuplicateUser(user);
        } catch (IllegalStateException e){
            e.printStackTrace();
        }
        // 비밀번호 암호화 후 파라미터로 받아온 유저 객체에 저장
        try {
            password = user.getUserPassword();
            encryptedPassword = encryptString(password);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        user.setUserPassword(encryptedPassword);

        // 리포지토리를 통하여 데이터베이스에 저장
        userRepository.save(user);
        return user.getId();
    }

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

    private void validateDuplicateUser(User user) {
        User duplicateUser = userRepository.findByUserId(user.getUserId());
        if(duplicateUser != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
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
        }

        // 사용자 입력 비밀번호 인코딩 후 비밀번호 대조
        if(!compareString(inputPassword, userPassword)) {
            return null;
        }

        return user;
    }

    private Boolean compareString (String a, String b){
        return a.equals(b);
    }

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByCookie(String cookie) {
        String userId = jwtService.getByCookie(cookie)
                .get("userId")
                .toString();
        return getUser(userId);
    }

    public void updateUserProfile(String cookie, String name, String nickname) {
        User user = getUserByCookie(cookie);
        user.setName(name);
        user.setNickname(nickname);
        userRepository.save(user);
    }

    public Map<String, Object> createResultBody(String cookie) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String token = cookie.split("=")[1];
            resultMap.put("token", token);
            String userId = jwtService.get(token).get("userId").toString();
            UserDto userData = UserDto.convert(getUser(userId));
            resultMap.put("userData", userData);
        } catch (RuntimeException e) {
            resultMap.put("message", "존재하지 않는 유저입니다.");
        }
        return resultMap;
    }
}
