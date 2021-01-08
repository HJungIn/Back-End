package com.project.gonggus.domain.post;

import com.project.gonggus.domain.comment.CommentDto;
import com.project.gonggus.domain.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class PostDto {

    private long id;
    private String title;
    private String category;
    private String content;
    private String goodsLink;
    private long currentNumberOfPeople;
    private long limitNumberOfPeople;
    private String deadline;
    private String createdDate;
    private boolean finishCheck;
    private UserDto owner;
    private List<CommentDto> comments;

    public static PostDto convert(Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .category(post.getCategory())
                .content(post.getContent())
                .goodsLink(post.getGoodsLink())
                .currentNumberOfPeople(post.getCurrentNumberOfPeople())
                .limitNumberOfPeople(post.getLimitNumberOfPeople())
                .deadline(new SimpleDateFormat("yyyy-MM-dd").format(post.getDeadline()))
                .createdDate(post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .finishCheck(post.getFinishCheck())
                .owner(UserDto.convert(post.getOwner()))
                .comments(post.getComments().stream().map(CommentDto::convert).collect(Collectors.toList()))
                .build();
    }

}
