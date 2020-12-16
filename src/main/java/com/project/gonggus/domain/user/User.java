package com.project.gonggus.domain.user;

import com.project.gonggus.domain.comment.Comment;

import com.project.gonggus.domain.userpost.UserPost;
import com.project.gonggus.domain.post.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userPassword;

    private String nickname;

    private String schoolName;

    @Builder
    public User(String name, String userId, String userPassword, String nickname, String schoolName){
        this.name=name;
        this.userId=userId;
        this.userPassword=userPassword;
        this.nickname=nickname;
        this.schoolName=schoolName;
    }


    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserPost> participatePosts = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Post> ownPosts = new ArrayList<>();

    private ArrayList<Long> bookmarkPosts = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> myComments = new ArrayList<>();

}
