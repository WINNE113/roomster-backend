package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.CommentPostDto;
import com.roomster.roomsterbackend.entity.Comment;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-07T22:55:17+0700",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
*/
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentPostDto entityToDTO(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentPostDto commentPostDto = new CommentPostDto();

        commentPostDto.setCreatedDate( comment.getCreatedDate() );
        commentPostDto.setCommentPostId( comment.getId() );
        commentPostDto.setUserId( comment.getUserId() );
        commentPostDto.setPostId( comment.getPostId() );
        commentPostDto.setTitle( comment.getTitle() );
        commentPostDto.setContent( comment.getContent() );
        commentPostDto.setStatus( comment.isStatus() );

        return commentPostDto;
    }

    @Override
    public Comment dtoToEntity(CommentPostDto commentPostDTO) {
        if ( commentPostDTO == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( commentPostDTO.getCommentPostId() );
        comment.setCreatedDate( commentPostDTO.getCreatedDate() );
        comment.setTitle( commentPostDTO.getTitle() );
        comment.setContent( commentPostDTO.getContent() );
        comment.setStatus( commentPostDTO.isStatus() );
        comment.setPostId( commentPostDTO.getPostId() );
        comment.setUserId( commentPostDTO.getUserId() );

        return comment;
    }

    @Override
    public Comment updateCommentPost(Comment oldComment, Comment newCommentPostEntity) {
        if ( newCommentPostEntity == null ) {
            return null;
        }

        oldComment.setId( newCommentPostEntity.getId() );
        oldComment.setCreatedDate( newCommentPostEntity.getCreatedDate() );
        oldComment.setModifiedDate( newCommentPostEntity.getModifiedDate() );
        oldComment.setCreatedBy( newCommentPostEntity.getCreatedBy() );
        oldComment.setModifiedBy( newCommentPostEntity.getModifiedBy() );
        oldComment.setTitle( newCommentPostEntity.getTitle() );
        oldComment.setContent( newCommentPostEntity.getContent() );
        oldComment.setStatus( newCommentPostEntity.isStatus() );
        oldComment.setPostId( newCommentPostEntity.getPostId() );
        oldComment.setUserId( newCommentPostEntity.getUserId() );

        return oldComment;
    }
}
