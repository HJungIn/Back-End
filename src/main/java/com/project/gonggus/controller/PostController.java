package com.project.gonggus.controller;

import com.project.gonggus.domain.post.PostDto;
import com.project.gonggus.domain.post.PostService;
import com.project.gonggus.domain.user.JwtService;
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

    private final PostService postService;
    private final UserService userService;
    private final JwtService jwtService;

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
    }

    @PostMapping("/makepostsubmit")
    public void makePostSubmit(@RequestBody Map<String, String> param,
                               @RequestHeader Map<String, String> res) throws Exception{
        String jwt = res.get("jwt");
        if (jwt == null || !jwtService.checkJwt(jwt)) {
            return ;
        }
        User user = jwtService.getUserByJwt(jwt);
        if(user==null)
            return ;

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
                                 @RequestBody Map<String, String> param,
                                 @RequestHeader Map<String, String> res) throws Exception{
        String jwt = res.get("jwt");
        if (jwt == null || !jwtService.checkJwt(jwt)) {
            return ;
        }
        User user = jwtService.getUserByJwt(jwt);
        if(user==null)
            return ;

        postService.updatePost(postId, param.get("title"), param.get("content"), param.get("category"),param.get("goodsLink"),param.get("limitNumberOfPeople"),param.get("deadline"));
    }


    @GetMapping("/post/{postId}")
    public PostDto detailPost(@PathVariable("postId") Long postId){
        return postService.getPostDto(postId);
    }


    @PostMapping("/post/{postId}/registerbookmark")
    public void registerBookmark(@PathVariable("postId") Long postId,
                                 @RequestHeader Map<String, String> res) throws Exception{
        String jwt = res.get("jwt");
        if (jwt == null || !jwtService.checkJwt(jwt)) {
            return ;
        }
        User user = jwtService.getUserByJwt(jwt);
        if(user==null)
            return ;

        postService.registerBookmark(user.getUserId(), postId);
    }

    @DeleteMapping("/post/{postId}/deletebookmark")
    public void deleteBookmark(@PathVariable("postId") Long postId,
                               @RequestHeader Map<String, String> res) throws Exception{
        String jwt = res.get("jwt");
        if (jwt == null || !jwtService.checkJwt(jwt)) {
            return ;
        }
        User user = jwtService.getUserByJwt(jwt);
        if(user==null)
            return ;

        postService.deleteBookmark(user.getUserId(), postId);
    }

    @PostMapping("/post/{postId}/participatepost")
    public void participatePost(@PathVariable("postId") Long postId,
                                @RequestHeader Map<String, String> res) throws Exception{
        String jwt = res.get("jwt");
        if (jwt == null || !jwtService.checkJwt(jwt)) {
            return ;
        }
        User user = jwtService.getUserByJwt(jwt);
        if(user==null)
            return ;

        postService.participatePost(user.getUserId(), postId);
    }

    @DeleteMapping("/post/{postId}/withdrawpost")
    public void withdrawPost(@PathVariable("postId") Long postId,
                             @RequestHeader Map<String, String> res) throws Exception{
        String jwt = res.get("jwt");
        if (jwt == null || !jwtService.checkJwt(jwt)) {
            return ;
        }
        User user = jwtService.getUserByJwt(jwt);
        if(user==null)
            return ;

        postService.withdrawPost(user.getUserId(), postId);
    }

    @DeleteMapping("/post/{postId}/deletepost")
    public void deletePost(@PathVariable("postId") Long postId,
                           @RequestHeader Map<String, String> res) throws Exception{
        String jwt = res.get("jwt");
        if (jwt == null || !jwtService.checkJwt(jwt)) {
            return ;
        }
        User user = jwtService.getUserByJwt(jwt);
        if(user==null)
            return ;

        postService.deletePost(user.getUserId(), postId);
    }
}

