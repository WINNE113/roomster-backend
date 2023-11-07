package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.entity.PostEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-07T22:55:17+0700",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
*/
@Component
@Qualifier("delegate")
public class PostMapperImpl_ implements PostMapper {

    @Override
    public PostDto entityToDto(PostEntity postEntity) {
        if ( postEntity == null ) {
            return null;
        }

        PostDto postDto = new PostDto();

        postDto.setPostId( postEntity.getId() );
        postDto.setTitle( postEntity.getTitle() );
        postDto.setAddress( postEntity.getAddress() );
        postDto.setDescription( postEntity.getDescription() );
        postDto.setObject( postEntity.getObject() );
        postDto.setConvenient( postEntity.getConvenient() );
        postDto.setSurroundings( postEntity.getSurroundings() );
        postDto.setDeleted( postEntity.isDeleted() );
        postDto.setAuthorId( postEntity.getAuthorId() );
        List<String> list = postEntity.getImageUrlList();
        if ( list != null ) {
            postDto.setImageUrlList( new ArrayList<String>( list ) );
        }
        else {
            postDto.setImageUrlList( null );
        }

        return postDto;
    }

    @Override
    public PostEntity dtoToEntity(PostDto postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        PostEntity postEntity = new PostEntity();

        postEntity.setAuthorId( postDTO.getAuthorId() );
        postEntity.setTitle( postDTO.getTitle() );
        postEntity.setAddress( postDTO.getAddress() );
        postEntity.setDescription( postDTO.getDescription() );
        postEntity.setObject( postDTO.getObject() );
        postEntity.setConvenient( postDTO.getConvenient() );
        postEntity.setSurroundings( postDTO.getSurroundings() );
        List<String> list = postDTO.getImageUrlList();
        if ( list != null ) {
            postEntity.setImageUrlList( new ArrayList<String>( list ) );
        }
        else {
            postEntity.setImageUrlList( null );
        }
        postEntity.setDeleted( postDTO.isDeleted() );

        return postEntity;
    }

    @Override
    public PostEntity updatePost(PostEntity oldPost, PostEntity newPost) {
        if ( newPost == null ) {
            return null;
        }

        oldPost.setCreatedDate( newPost.getCreatedDate() );
        oldPost.setModifiedDate( newPost.getModifiedDate() );
        oldPost.setCreatedBy( newPost.getCreatedBy() );
        oldPost.setModifiedBy( newPost.getModifiedBy() );
        oldPost.setAuthorId( newPost.getAuthorId() );
        oldPost.setTitle( newPost.getTitle() );
        oldPost.setAddress( newPost.getAddress() );
        oldPost.setDescription( newPost.getDescription() );
        oldPost.setObject( newPost.getObject() );
        oldPost.setConvenient( newPost.getConvenient() );
        oldPost.setSurroundings( newPost.getSurroundings() );
        if ( oldPost.getImageUrlList() != null ) {
            List<String> list = newPost.getImageUrlList();
            if ( list != null ) {
                oldPost.getImageUrlList().clear();
                oldPost.getImageUrlList().addAll( list );
            }
            else {
                oldPost.setImageUrlList( null );
            }
        }
        else {
            List<String> list = newPost.getImageUrlList();
            if ( list != null ) {
                oldPost.setImageUrlList( new ArrayList<String>( list ) );
            }
        }
        oldPost.setDeleted( newPost.isDeleted() );
        oldPost.setRoom( newPost.getRoom() );
        oldPost.setPostType( newPost.getPostType() );

        return oldPost;
    }
}
