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

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private final int ONE_DAY = 86400;

    // 로그인
    public Map<String, Object> login (String id, String password) {
        // 입력된 아이디, 비밀번호 길이 검사
        if(id.length() <= 0 || password.length() <= 0) {
            return null;
        }

        // 유저 데이터 요청
        User user = userRepository.findByUserId(id);
        String token = jwtService.create(user);

        // 사용자 입력 비밀번호 인코딩 후 비밀번호 대조
        String inputPassword = encryptString(password);
        if(!compareString(inputPassword, user.getUserPassword())) {
            return null;
        }

        return createResultMap(user, token);
    }

    // 회원가입
    public void register (Map<String, Object> body) {
        User user = getUserByRequestBody(body);
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
    }

    // 로그인 확인
    public Map<String, Object> check (String token) {
        Map<String, Object> resultMap = new HashMap<>();
        User user = getUser(
                jwtService.get(token)
                        .get("userId")
                        .toString()
        );
        return createResultMap(user, token);
    }

    // 유저 정보 수정
    public Map<String, Object> updateProfile(String token, String name, String nickname) {
        String userId = jwtService.get(token)
                .get("userId")
                .toString();
        User user = userRepository.findByUserId(userId);
        user.setName(name);
        user.setNickname(nickname);
        userRepository.save(user);
        return createResultMap(user, token);
    }

    // 쿠키 생성
    public Cookie setAuthCookie (String token, int expday) {
        Cookie cookie = new Cookie("auth_token", token);
        cookie.setMaxAge(expday * ONE_DAY);
        cookie.setHttpOnly(true);
        return cookie;
    }

    //
    private User getUserByRequestBody(Map<String, Object> body) {
        String name = body.get("name").toString();
        String userId = body.get("userId").toString();
        String userPassword = body.get("userPassword").toString();
        String nickname = body.get("nickname").toString();
        String schoolName = body.get("schoolName").toString();
        return new User(name, userId, userPassword, nickname, schoolName);
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

    private Map<String, Object> createResultMap(User user, String token){
        Map<String, Object> resultMap = new HashMap<>();
        UserDto userData = UserDto.convert(user);
        resultMap.put("token", token);
        resultMap.put("userData", userData);
        return resultMap;
    }
}
