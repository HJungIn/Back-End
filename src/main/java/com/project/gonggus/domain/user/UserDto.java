package com.project.gonggus.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String name;
    private String userId;
    private String nickname;
    private String schoolName;

    public static UserDto convert(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .schoolName(user.getSchoolName())
                .build();
    }

}
