package com.project.gonggus.domain.user;

import com.project.gonggus.domain.comment.Comment;
import com.project.gonggus.domain.userpost.UserPost;
import com.project.gonggus.domain.post.Post;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Getter
public class User {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @Column(nullable = false) String name;
    private @Column(nullable = false) String userId;
    private @Column(nullable = false) String userPassword;
    private String nickname;
    private String schoolName;

    public User() { }

    @Builder
    public User(String name, String userId, String userPassword, String nickname, String schoolName) {
        this.name = name;
        this.userId = userId;
        this.userPassword = userPassword;
        this.nickname = nickname;
        this.schoolName = schoolName;
    }



//    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<UserPost> participatePosts = new ArrayList<>();
//
//    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<Post> ownPosts = new ArrayList<>();
//
//    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<Post> bookmarkPosts = new ArrayList<>();
//
//    @OneToMany(mappedBy = "writer", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<Comment> myComments = new ArrayList<>();
}
