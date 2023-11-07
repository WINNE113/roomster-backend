package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.CommentPostDto;
import com.roomster.roomsterbackend.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "commentPostId", source = "id")
    @Mapping(target = "createdDate", source = "createdDate")
    CommentPostDto entityToDTO(Comment comment);

    @Mapping(target = "id", source = "commentPostId")
    Comment dtoToEntity(CommentPostDto commentPostDTO);

    Comment updateCommentPost(@MappingTarget Comment oldComment, Comment newCommentPostEntity);
}
