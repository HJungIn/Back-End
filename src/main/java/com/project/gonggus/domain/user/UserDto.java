package com.project.gonggus.domain.user;

import com.project.gonggus.domain.userpost.UserPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
    private List<Long> bookmarkPosts;
    private List<Long> participatePosts;

    public static UserDto convert(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .schoolName(user.getSchoolName())
                .bookmarkPosts(user.getBookmarkPosts())
                .participatePosts(user.getParticipatePosts().stream().map(UserPost::getId).collect(Collectors.toList()))
                .build();
    }

}
