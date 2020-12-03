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
    private Boolean isEdit;
    private String createdDate;

    public static CommentDto convert(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .writer(comment.getWriter().getUserId())
                .content(comment.getContent())
                .isEdit(comment.getIsEdit())
                .createdDate(comment.getCreatedDate())
                .build();
    }
}
