package com.project.gonggus.controller;

import com.project.gonggus.domain.post.PostDto;
import com.project.gonggus.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signupuser")
    public void signUpUser(@RequestBody Map<String, String> param){
        userService.saveUser(param);
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
}
