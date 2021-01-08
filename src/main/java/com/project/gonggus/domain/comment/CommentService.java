package com.project.gonggus.domain.comment;

import com.project.gonggus.domain.post.Post;
import com.project.gonggus.domain.post.PostService;
import com.project.gonggus.domain.user.User;
import com.project.gonggus.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostService postService;

    public Long saveComment(String userId, Long postId, String content, Boolean isEdit, String createdDate) {

        User user = userRepository.findByUserId(userId);
        Post post = postService.getPost(postId);
        if(post == null){
            return Long.valueOf(0);
        }

        Comment comment = new Comment(user, post, content, isEdit, createdDate);
        Comment save_comment = commentRepository.save(comment);

        return save_comment.getId();
    }

    public CommentDto getCommentDto(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        return comment != null ? CommentDto.convert(comment) : null;
    }

    public void updateComment(String userId, Long postId, Long commentId, String content) {
        User user = userRepository.findByUserId(userId);
        Post post = postService.getPost(postId);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(user==null || post == null || comment==null){
            return;
        }
        comment.setContent(content);
    }


    public void deleteComment(String userId, Long commentId) {
        User user = userRepository.findByUserId(userId);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(user != comment.getWriter())
            return;

        commentRepository.delete(comment);
    }
}
