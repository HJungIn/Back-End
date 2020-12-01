package com.project.gonggus.controller;

import com.project.gonggus.domain.comment.CommentDto;
import com.project.gonggus.domain.comment.CommentService;
import com.project.gonggus.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    public User user = new User("na","ia","pa","",""); //임시용
    @PostMapping("/post/{postId}/writecommentsubmit")
    public void writeCommentSubmit(@PathVariable("postId") Long postId,
                                   @RequestBody Map<String, String> param){
//                             @RequestParam("content") String content){

        //        ("user"); //현재 로그인중인 user 와 writer이 동일한지 확인
        commentService.saveComment(user.getUserId(), postId, param.get("content"));
    }

    @GetMapping("/post/{postId}/updatecomment/{commentId}")
    public CommentDto updateComment(@PathVariable("postId") Long postId,
                                    @PathVariable("commentId") Long commentId){
        //        ("user"); //현재 로그인중인 user
        return commentService.getCommentDto(commentId);
    }

    @PutMapping("/post/{postId}/updatecommentsubmit/{commentId}")
    public void updateCommentSubmit(@PathVariable("postId") Long postId,
                                    @PathVariable("commentId") Long commentId,
                                    @RequestBody Map<String, String> param){
//                                    @RequestParam("content") String content){

        //        ("user"); //현재 로그인중인 user 와 writer이 동일한지 확인
        commentService.updateComment(user.getUserId(), postId, commentId, param.get("content"));
    }

    @DeleteMapping("/post/{postId}/deletecomment/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId){
        //        ("user"); //현재 로그인중인 user
//        commentService.deleteComment("a", commentId); //댓글 글쓴이와 현재 로그인중인 유저가 달라삭제 안되는 경우
        commentService.deleteComment(user.getUserId(), commentId); //같아서 삭제되는 경우
    }

}