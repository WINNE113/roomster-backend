package com.roomster.roomsterbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentPostDto {
    private Long commentPostId;
    private Long userId;
    private Long postId;
    private String title;
    private String content;
    private boolean status;
    private Date createdDate;
}
