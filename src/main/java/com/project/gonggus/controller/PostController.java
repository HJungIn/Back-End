package com.project.gonggus.controller;

import com.project.gonggus.domain.post.Post;
import com.project.gonggus.domain.post.PostService;
import com.project.gonggus.domain.user.User;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{category}")
    public List<Post> categoryPost(@PathVariable("category") String category){
        return postService.getCategoryPosts(category);
    }

    @GetMapping("/searchpost")
    public List<Post> searchPost(@RequestParam("search") String searchTitle){
        return postService.getSearchPosts(searchTitle);
    }



    public User user = new User("na","ia","pa","",""); //임시용
    @RequestMapping("/makepost")
    public void makePost(){
//        ("user"); //현재 로그인 중인 user가 존재하는가
    }

    @PostMapping("/makepostsubmit")
    public void makePostSubmit(@RequestParam("title") String title,
                                 @RequestParam("content") String content,
                                 @RequestParam("category") String category,
                                 @RequestParam("goodsLink") String goodsLink,
                                 @RequestParam("limitNumberOfPeople") Long limitNumberOfPeople,
                                 @RequestParam("deadline") String deadline){

//        ("user"); //현재 로그인중 user 정보가져오기
        postService.savePost(user.getUserId(), title, content, category, goodsLink, limitNumberOfPeople, deadline);
    }

    @GetMapping("/post/{postId}/update")
    public Post updatePost(@PathVariable("postId") Long postId){
        return postService.getPost(postId).get();
    }

    @PutMapping("/updatepostsubmit/{postId}")
    public void updatePostSubmit(@PathVariable("postId") Long postId,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam("category") String category,
                             @RequestParam("goodsLink") String goodsLink,
                             @RequestParam("limitNumberOfPeople") Long limitNumberOfPeople,
                             @RequestParam("deadline") String deadline){

//        ("user"); //현재 로그인중인 user
        postService.updatePost(postId, title,content,category,goodsLink,limitNumberOfPeople,deadline);
    }

    //1.이 페이지에 댓글하고 다 보일건데.. 로그인 한 user가 참여했으면 보이고 참여안했으면 안보임
    @GetMapping("/post/{postId}")
    public Post detailPost(@PathVariable("postId") Long postId){
        return postService.getPost(postId).get();
    }



}
