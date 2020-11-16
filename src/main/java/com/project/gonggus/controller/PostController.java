package com.project.gonggus.controller;

import com.project.gonggus.domain.post.Post;
import com.project.gonggus.domain.post.PostService;
import com.project.gonggus.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @RequestMapping("/{category}")
    public String categoryPost(Model model, @PathVariable("category") String category){
        List<Post> categoryPosts = postService.getCategoryPosts(category);
        model.addAttribute("categoryPosts",categoryPosts);
        return "categorypost";
    }


    @RequestMapping("/searchpost")
    public String searchPost(Model model, @RequestParam("search") String searchTitle){
        List<Post> searchPosts = postService.getSearchPosts(searchTitle);
        model.addAttribute("searchPosts",searchPosts);
        return "searchpost";
    }


//    private HttpSession httpSession;
    public User user = new User("na","ia","pa","",""); //임시용
    @RequestMapping("/makepost")
    public String makePost(){
//        httpSession.getAttribute("user"); //현재 로그인 중인 user가 존재하는가
        return "makepost";
    }

    @RequestMapping("/makepostsubmit")
    public String makePostSubmit(@RequestParam("title") String title,
                                 @RequestParam("content") String content,
                                 @RequestParam("category") String category,
                                 @RequestParam("goodsLink") String goodsLink,
                                 @RequestParam("limitNumberOfPeople") Long limitNumberOfPeople,
                                 @RequestParam("deadline") String deadline){

//        User user = (User)httpSession.getAttribute("user"); //현재 로그인중 user 정보가져오기
        postService.savePost(user.getUserId(), title, content, category, goodsLink, limitNumberOfPeople, deadline);
        return "redirect:/";
    }

    @RequestMapping("/post/{postId}/update")
    public String updatePost(Model model, @PathVariable("postId") Long postId){

        Post post = postService.getPost(postId).get();
        model.addAttribute("post", post);
        return "updatepost";
    }

    @RequestMapping("/updatepostsubmit/{postId}")
    public String updatePostSubmit(@PathVariable("postId") Long postId,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam("category") String category,
                             @RequestParam("goodsLink") String goodsLink,
                             @RequestParam("limitNumberOfPeople") Long limitNumberOfPeople,
                             @RequestParam("deadline") String deadline){

//        User user = (User)httpSession.getAttribute("user"); //현재 로그인중인 user
        postService.updatePost(postId, title,content,category,goodsLink,limitNumberOfPeople,deadline);
        return "redirect:/post/"+postId;
    }

    //1.이 페이지에 댓글하고 다 보일건데.. 로그인 한 user가 참여했으면 보이고 참여안했으면 안보임
    @RequestMapping("/post/{postId}")
    public String detailPost(Model model, @PathVariable("postId") Long postId){
        Post post = postService.getPost(postId).get();
        model.addAttribute("post",post);
        return "detailpost";
    }



}
