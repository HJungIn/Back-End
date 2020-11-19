package com.project.gonggus.domain.comment;

import com.project.gonggus.domain.post.Post;
import com.project.gonggus.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User writer;

    private String content;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Builder
    public Comment(User writer, Post post, String content){
        this.writer = writer;
        this.post = post;
        this.content = content;
        this.setWriter(writer);
        this.setPost(post);
    }

    public void  setWriter(User writer){
        this.writer = writer;
//        writer.getMyComments().add(this);
    }

    public void setPost(Post post){
        this.post = post;
        post.getComments().add(this);
    }
}
