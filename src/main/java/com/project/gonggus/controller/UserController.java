package com.project.gonggus.controller;

import com.project.gonggus.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
