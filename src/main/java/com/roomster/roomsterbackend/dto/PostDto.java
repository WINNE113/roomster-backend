package com.roomster.roomsterbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {
    private Long postId;
    private String title;
    private String address;
    private String description;
    private String object;
    private String convenient;
    private String surroundings;
    private String postType;
    private boolean isDeleted;
    private InforRoomDto roomDto;
    private List<String> imageUrlList;
    private List<CommentPostDto> commentPostDTOList;
    private List<RatingDto> ratingDTOList;
}
