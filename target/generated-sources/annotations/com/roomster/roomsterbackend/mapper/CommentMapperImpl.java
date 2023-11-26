package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.comment.CommentPostDto;
import com.roomster.roomsterbackend.entity.CommentEnity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-23T22:55:08+0700",
    comments = "version: 1.4.0.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentPostDto entityToDTO(CommentEnity commentEnity) {
        if ( commentEnity == null ) {
            return null;
        }

        CommentPostDto commentPostDto = new CommentPostDto();

        commentPostDto.setCommentPostId( commentEnity.getId() );
        commentPostDto.setCreatedDate( commentEnity.getCreatedDate() );
        commentPostDto.setUserId( commentEnity.getUserId() );
        commentPostDto.setPostId( commentEnity.getPostId() );
        commentPostDto.setTitle( commentEnity.getTitle() );
        commentPostDto.setContent( commentEnity.getContent() );
        commentPostDto.setStatus( commentEnity.isStatus() );

        return commentPostDto;
    }

    @Override
    public CommentEnity dtoToEntity(CommentPostDto commentPostDTO) {
        if ( commentPostDTO == null ) {
            return null;
        }

        CommentEnity commentEnity = new CommentEnity();

        commentEnity.setId( commentPostDTO.getCommentPostId() );
        commentEnity.setCreatedDate( commentPostDTO.getCreatedDate() );
        commentEnity.setTitle( commentPostDTO.getTitle() );
        commentEnity.setContent( commentPostDTO.getContent() );
        commentEnity.setStatus( commentPostDTO.isStatus() );
        commentEnity.setPostId( commentPostDTO.getPostId() );
        commentEnity.setUserId( commentPostDTO.getUserId() );

        return commentEnity;
    }
}
