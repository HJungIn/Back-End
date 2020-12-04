package com.project.gonggus.domain.user;

import com.project.gonggus.domain.comment.Comment;
import com.project.gonggus.domain.post.Post;
import com.project.gonggus.domain.post.PostDto;
import com.project.gonggus.domain.userpost.UserPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private ArrayList<Long> bookmarkPosts;

    public static UserDto convert(User user){
        List<Long> participate = user.getParticipatePosts().stream()
                .map(m -> m.getId()).collect(Collectors.toList());

        List<Long> own = user.getOwnPosts().stream()
                .map(m -> m.getId()).collect(Collectors.toList());

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
