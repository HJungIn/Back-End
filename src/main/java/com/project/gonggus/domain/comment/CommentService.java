package com.project.gonggus.domain.comment;

import com.project.gonggus.domain.post.Post;
import com.project.gonggus.domain.post.PostService;
import com.project.gonggus.domain.user.User;
import com.project.gonggus.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public void saveComment(String userId, Long postId, String content, Boolean isEdit, String createdDate) {

        User user = userService.getUser(userId);
        Post post = postService.getPost(postId);
        if(post == null){
            return;
        }

        Comment comment = new Comment(user, post, content, isEdit, createdDate);
        commentRepository.save(comment);

    }

    public CommentDto getCommentDto(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        return comment != null ? CommentDto.convert(comment) : null;
    }

    public void updateComment(String userId, Long postId, Long commentId, String content) {
        User user = userService.getUser(userId);
        Post post = postService.getPost(postId);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(user==null || post == null || comment==null){
            return;
        }
        comment.setContent(content);
    }


    public void deleteComment(String userId, Long commentId) {
        User user = userService.getUser(userId);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(user != comment.getWriter())
            return;

        commentRepository.delete(comment);
    }
}
