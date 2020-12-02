package com.project.gonggus.domain.user;

import com.project.gonggus.domain.comment.Comment;
import com.project.gonggus.domain.post.Post;
import com.project.gonggus.domain.userpost.UserPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String name;
    private String userId;
    private String nickname;
    private String schoolName;
    private List<UserPost> participatePosts = new ArrayList<>();
    private List<Post> ownPosts = new ArrayList<>();
    private ArrayList<Long> bookmarkPosts = new ArrayList<>();
    private List<Comment> myComments = new ArrayList<>();

    public static UserDto convert(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .schoolName(user.getSchoolName())
                        .participatePosts(user.getParticipatePosts())
                        .ownPosts(user.getOwnPosts())
                        .bookmarkPosts(user.getBookmarkPosts())
                        .myComments(user.getMyComments())
                .build();
    }

}
