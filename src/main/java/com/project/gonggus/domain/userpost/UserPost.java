package com.project.gonggus.domain.userpost;

import com.project.gonggus.domain.post.Post;
import com.project.gonggus.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class UserPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Builder
    public UserPost(User user, Post post){
        this.user = user;
        this.post = post;
        this.setUser(user);
        this.setPost(post);
    }

    public void setUser(User user){
        this.user =user;
//        user.getParticipatePosts().add(this);
    }
    public void setPost(Post post){
        this.post = post;
        post.getParticipateUsers().add(this);
    }
}
