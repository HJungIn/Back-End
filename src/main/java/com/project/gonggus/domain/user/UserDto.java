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
    private List<Long> participatePosts;
    private List<Long> ownPosts;
    private List<Long> bookmarkPosts;

    public static UserDto convert(User user){
        List<Long> participate = new ArrayList<>();
        for(UserPost up: user.getParticipatePosts()) {
            participate.add(up.getPost().getId());
        }
        List<Long> own = new ArrayList<>();
        for(Post up: user.getOwnPosts()) {
            own.add(up.getId());
        }

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .schoolName(user.getSchoolName())
                .participatePosts(participate)
                .ownPosts(own)
                .bookmarkPosts(user.getBookmarkPosts())
                .build();
    }

}
