package com.project.gonggus.controller;

import com.project.gonggus.domain.post.PostDto;
import com.project.gonggus.domain.user.JwtService;
import com.project.gonggus.domain.user.UserDto;
import com.project.gonggus.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/signupuser")
    public void signUpUser(@RequestBody Map<String, String> param){
        userService.saveUser(param);
    }

    @GetMapping("/user/{id}/mypage")
    public UserDto myPage(@PathVariable("id") Long userIdx){
        return userService.getUserByUserDto(userIdx);
    }

    @PutMapping("/user/{id}/editmyinfo")
    public void editMyInfo(@PathVariable("id") Long userIdx,
                             @RequestBody Map<String, String> param){
        userService.editUserInfo(userIdx, param);
    }

    @GetMapping("/user/{id}/mybookmarkposts")
    public List<PostDto> usersBookmarkPosts(@PathVariable("id") Long userIdx){
        return userService.getUsersBookmarkPosts(userIdx);
    }

    @GetMapping("/user/{id}/myparticipateposts")
    public List<PostDto> usersParticipatePosts(@PathVariable("id") Long userIdx){
        return userService.getUserParticipatePosts(userIdx);
    }

    @PostMapping("/signinuser")
    public String signInUser(@RequestBody Map<String, String> param) throws Exception{

        if(!userService.checkForLogin(param))
            return "아이디가 없거나, 비밀번호가 맞지 않습니다.";

        return jwtService.makeJwt(param);
    }
}
