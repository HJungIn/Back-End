package com.project.gonggus.controller;

import com.project.gonggus.domain.post.PostDto;
import com.project.gonggus.domain.user.JwtService;
import com.project.gonggus.domain.user.User;
import com.project.gonggus.domain.user.UserDto;
import com.project.gonggus.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/signupuser")
    public void signUpUser(@RequestBody Map<String, String> param){
        userService.saveUser(param);
    }

    @GetMapping("/user/{id}/mypage")
    public UserDto myPage(@PathVariable("id") Long userIdx,
                          @RequestHeader Map<String, String> res) throws Exception{
        String jwt = res.get("jwt");
        if (jwt == null || !jwtService.checkJwt(jwt)) {
            return null;
        }
        User user = jwtService.getUserByJwt(jwt);
        if(user==null || userIdx!=user.getId())
            return null;

        return userService.getUserByUserDto(userIdx);
    }

    @PutMapping("/user/{id}/editmyinfo")
    public void editMyInfo(@PathVariable("id") Long userIdx,
                             @RequestBody Map<String, String> param,
                           @RequestHeader Map<String, String> res) throws Exception{
        String jwt = res.get("jwt");
        if (jwt == null || !jwtService.checkJwt(jwt)) {
            return ;
        }
        User user = jwtService.getUserByJwt(jwt);
        if(user==null || userIdx!=user.getId())
            return ;

        userService.editUserInfo(userIdx, param);
    }

    @GetMapping("/user/{id}/mybookmarkposts")
    public List<PostDto> usersBookmarkPosts(@PathVariable("id") Long userIdx,
                                            @RequestHeader Map<String, String> res) throws Exception{
        String jwt = res.get("jwt");
        if (jwt == null || !jwtService.checkJwt(jwt)) {
            return null;
        }
        User user = jwtService.getUserByJwt(jwt);
        if(user==null || userIdx!=user.getId())
            return null;

        return userService.getUsersBookmarkPosts(userIdx);
    }

    @GetMapping("/user/{id}/myparticipateposts")
    public List<PostDto> usersParticipatePosts(@PathVariable("id") Long userIdx,
                                               @RequestHeader Map<String, String> res) throws Exception{
        String jwt = res.get("jwt");
        if (jwt == null || jwt.equals("") || !jwtService.checkJwt(jwt)) {
            return null;
        }
        User user = jwtService.getUserByJwt(jwt);
        if(user==null || userIdx!=user.getId())
            return null;

        return userService.getUserParticipatePosts(userIdx);
    }

//    @PostMapping("/signinuser")
//    public String signInUser(@RequestBody Map<String, String> param) throws Exception{
//
//        if(!userService.checkForLogin(param))
//            return "아이디가 없거나, 비밀번호가 맞지 않습니다.";
//
//        return jwtService.makeJwt(param);
//    }

    @PostMapping("/signinuser")
    public ResponseEntity<Map<String, Object>> signInUser(@RequestBody Map<String, String> param) throws Exception{

        if(!userService.checkForLogin(param))
            return null;

        Map<String, Object> map = new HashMap<>();
        map.put("userData", UserDto.convert(userService.getUser(param.get("userId"))));
        map.put("jwt", jwtService.makeJwt(param));

        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.ACCEPTED);
    }

}
