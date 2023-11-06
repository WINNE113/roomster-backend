package com.roomster.roomsterbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String post_type;
    private boolean isDeleted;
    private InforRoomDto roomDto;
    private List<String> imageUrlList;
    private List<CommentPostDto> commentPostDTOList;
    private List<RatingDto> ratingDTOList;

    public PostDto(Long postId, String title, String address, String description, String object, String convenient, String surroundings, String post_type, boolean isDeleted, InforRoomDto roomDto) {
        this.postId = postId;
        this.title = title;
        this.address = address;
        this.description = description;
        this.object = object;
        this.convenient = convenient;
        this.surroundings = surroundings;
        this.post_type = post_type;
        this.isDeleted = isDeleted;
        this.roomDto = roomDto;
    }
}
