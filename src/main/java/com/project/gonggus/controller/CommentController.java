package com.project.gonggus.controller;

import com.project.gonggus.domain.comment.CommentDto;
import com.project.gonggus.domain.comment.CommentService;
import com.project.gonggus.domain.user.JwtService;
import com.project.gonggus.domain.user.User;
import com.project.gonggus.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private final CommentService commentService;
    @Autowired
    private final UserService userService;

    @PostMapping("/post/{postId}/writecommentsubmit")
    public void writeCommentSubmit(@PathVariable("postId") Long postId,
                                   @RequestBody Map<String, String> param,
                                   @RequestHeader(value="Cookie") String cookie){
//                             @RequestParam("content") String content){
        User user = userService.getUserByCookie(cookie);
        commentService.saveComment(user.getUserId(), postId, param.get("content"));
    }

    @GetMapping("/post/{postId}/updatecomment/{commentId}")
    public CommentDto updateComment(@PathVariable("postId") Long postId,
                                    @PathVariable("commentId") Long commentId,
                                    @RequestHeader(value="Cookie") String cookie){
        User user = userService.getUserByCookie(cookie);
        return commentService.getCommentDto(commentId);
    }

    @PutMapping("/post/{postId}/updatecommentsubmit/{commentId}")
    public void updateCommentSubmit(@PathVariable("postId") Long postId,
                                    @PathVariable("commentId") Long commentId,
                                    @RequestBody Map<String, String> param,
                                    @RequestHeader(value="Cookie") String cookie){
//                                    @RequestParam("content") String content){
        User user = userService.getUserByCookie(cookie);
        commentService.updateComment(user.getUserId(), postId, commentId, param.get("content"));
    }

    @DeleteMapping("/post/{postId}/deletecomment/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId,
                              @RequestHeader(value="Cookie") String cookie){
        User user = userService.getUserByCookie(cookie);
//        commentService.deleteComment("a", commentId); //댓글 글쓴이와 현재 로그인중인 유저가 달라삭제 안되는 경우
        commentService.deleteComment(user.getUserId(), commentId); //같아서 삭제되는 경우
    }

}
