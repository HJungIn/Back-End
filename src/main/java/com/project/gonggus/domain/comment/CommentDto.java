package com.project.gonggus.domain.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String writer;
    private String content;

    public static CommentDto convert(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .writer(comment.getWriter().getUserId())
                .content(comment.getContent())
                .build();
    }
}
