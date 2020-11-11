package com.project.gonggus.controller;

import com.project.gonggus.domain.post.Post;
import com.project.gonggus.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @RequestMapping("/post")
    public String categoryPost(Model model, @RequestParam("category") String category){
        List<Post> categoryPosts = postService.getCategoryPosts(category);
        model.addAttribute("categoryPosts",categoryPosts);
        return "categorypost";
    }


    @RequestMapping("/search")
    public String searchPost(Model model, @RequestParam("search") String searchTitle){
        List<Post> searchPosts = postService.getSearchPosts(searchTitle);
        model.addAttribute("searchPosts",searchPosts);
        return "searchpost";
    }

}
