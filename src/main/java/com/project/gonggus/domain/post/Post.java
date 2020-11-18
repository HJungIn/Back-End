package com.project.gonggus.domain.post;

import com.project.gonggus.domain.BaseTimeEntity;
import com.project.gonggus.domain.userpost.UserPost;
import com.project.gonggus.domain.comment.Comment;
import com.project.gonggus.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String category;

    private String content;

    private String goodsLink;

    private Long currentNumberOfPeople;

    private Long limitNumberOfPeople;

    private Date deadline;

    private boolean finishCheck;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User owner;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserPost> participateUsers = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(User owner, String title, String content, String category, String goodsLink, Long currentNumberOfPeople,Long limitNumberOfPeople, Date deadline,boolean finishCheck){
        this.owner = owner;
        this.title = title;
        this.category = category;
        this.content = content;
        this.goodsLink = goodsLink;
        this.currentNumberOfPeople = currentNumberOfPeople;
        this.limitNumberOfPeople = limitNumberOfPeople;
        this.deadline = deadline;
        this.finishCheck = finishCheck;
        this.setOwner(owner);
    }

    public void  setOwner(User owner){
        this.owner = owner;
        owner.getOwnPosts().add(this);
    }
}
