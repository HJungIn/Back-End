package com.project.gonggus.domain.comment;

import com.project.gonggus.domain.post.Post;
import com.project.gonggus.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User writer;

    private String content;

    private Boolean isEdit;

    private String createdDate;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Builder
    public Comment(User writer, Post post, String content, Boolean isEdit, String createdDate){
        this.writer = writer;
        this.post = post;
        this.content = content;
        this.isEdit = isEdit;
        this.createdDate = createdDate;
        this.setWriter(writer);
        this.setPost(post);
    }

    public void setWriter(User writer){
        this.writer = writer;
        writer.getMyComments().add(this);
    }

    public void setPost(Post post){
        this.post = post;
        post.getComments().add(this);
    }
}