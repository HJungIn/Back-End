package com.project.gonggus.controller;

import com.project.gonggus.domain.post.PostDto;
import com.project.gonggus.domain.post.PostService;
import com.project.gonggus.domain.user.User;
import com.project.gonggus.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private final PostService postService;
    @Autowired
    private final UserService userService;

    public User user = new User("a","a@n","a","aa","aa"); //임시용

    @GetMapping("/{category}")
    public List<PostDto> categoryPost(@PathVariable("category") String category){
        return postService.getCategoryPosts(category);
    }

    @GetMapping("/searchpost")
    public List<PostDto> searchPost(@RequestParam("search") String searchTitle) {
        return postService.getSearchPosts(searchTitle);
    }

    @RequestMapping("/makepost")
    public void makePost(){
//        User user = userService.getUserByCookie(cookie);
    }

    @PostMapping("/makepostsubmit")
    public void makePostSubmit(@RequestBody Map<String, String> param){
//        User user = userService.getUserByCookie(cookie);
        postService.savePost(user.getUserId(), param.get("title"),
                param.get("content"),
                param.get("category"),
                param.get("goodsLink"),
                param.get("limitNumberOfPeople"),
                param.get("deadline"));
    }

    @GetMapping("/post/{postId}/update")
    public PostDto updatePost(@PathVariable("postId") Long postId){
        return postService.getPostDto(postId);
    }

    @PutMapping("/updatepostsubmit/{postId}")
    public void updatePostSubmit(@PathVariable("postId") Long postId,
                                 @RequestBody Map<String, String> param){

//        User user = userService.getUserByCookie(cookie); //현재 로그인중인 user
        postService.updatePost(postId, param.get("title"), param.get("content"), param.get("category"),param.get("goodsLink"),param.get("limitNumberOfPeople"),param.get("deadline"));
    }


    @GetMapping("/post/{postId}")
    public PostDto detailPost(@PathVariable("postId") Long postId){
        return postService.getPostDto(postId);
    }


    @PostMapping("/post/{postId}/registerbookmark")
    public void registerBookmark(@PathVariable("postId") Long postId){
//        User user = userService.getUserByCookie(cookie);
        postService.registerBookmark(user.getUserId(), postId);
    }

    @DeleteMapping("/post/{postId}/deletebookmark")
    public void deleteBookmark(@PathVariable("postId") Long postId){
//        User user = userService.getUserByCookie(cookie);
        postService.deleteBookmark(user.getUserId(), postId);
    }

    @PostMapping("/post/{postId}/participatepost")
    public void participatePost(@PathVariable("postId") Long postId){
//        User user = userService.getUserByCookie(cookie);
        postService.participatePost(user.getUserId(), postId);
    }

    @DeleteMapping("/post/{postId}/withdrawpost")
    public void withdrawPost(@PathVariable("postId") Long postId){
//        User user = userService.getUserByCookie(cookie);
        postService.withdrawPost(user.getUserId(), postId);
    }

    @DeleteMapping("/post/{postId}/deletepost")
    public void deletePost(@PathVariable("postId") Long postId){

//        User user = userService.getUserByCookie(cookie);
        postService.deletePost(user.getUserId(), postId);
    }
}

