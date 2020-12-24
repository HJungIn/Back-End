package com.project.gonggus.controller;

import com.project.gonggus.domain.comment.CommentDto;
import com.project.gonggus.domain.comment.CommentService;
import com.project.gonggus.domain.user.User;
import com.project.gonggus.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    public User user = new User("a","a@n","a","aa","aa"); //임시용
    @PostMapping("/post/{postId}/writecommentsubmit")
    public CommentDto writeCommentSubmit(@PathVariable("postId") Long postId,
                                         @RequestBody Map<String, String> param){
//        User user = userService.getUserByCookie(cookie);
        Long commentId = commentService.saveComment(user.getUserId(), postId, param.get("content"), Boolean.valueOf(param.get("isEdit")), param.get("createdDate"));
        return commentService.getCommentDto(commentId);
    }

    @GetMapping("/post/{postId}/updatecomment/{commentId}")
    public CommentDto updateComment(@PathVariable("postId") Long postId,
                                    @PathVariable("commentId") Long commentId){
//        User user = userService.getUserByCookie(cookie);
        return commentService.getCommentDto(commentId);
    }

    @PutMapping("/post/{postId}/updatecommentsubmit/{commentId}")
    public void updateCommentSubmit(@PathVariable("postId") Long postId,
                                    @PathVariable("commentId") Long commentId,
                                    @RequestBody Map<String, String> param){
//        User user = userService.getUserByCookie(cookie);
        commentService.updateComment(user.getUserId(), postId, commentId, param.get("content"));
    }

    @DeleteMapping("/post/{postId}/deletecomment/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId){
//        User user = userService.getUserByCookie(cookie);
        commentService.deleteComment(user.getUserId(), commentId); //같아서 삭제되는 경우
    }

}