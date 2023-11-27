package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.post.PostDto;
import com.roomster.roomsterbackend.dto.user.UserDto;
import com.roomster.roomsterbackend.entity.PostEntity;
import com.roomster.roomsterbackend.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-27T20:31:43+0700",
    comments = "version: 1.4.0.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
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
        postDto.setAuthorId( userEntityToUserDto( postEntity.getAuthorId() ) );
        postDto.setRotation( postEntity.getRotation() );
        postDto.setCreatedDate( postEntity.getCreatedDate() );
        postDto.setModifiedDate( postEntity.getModifiedDate() );
        postDto.setModifiedBy( postEntity.getModifiedBy() );
        List<String> list = postEntity.getImageUrlList();
        if ( list != null ) {
            postDto.setImageUrlList( new ArrayList<String>( list ) );
        }

        return postDto;
    }

    @Override
    public PostEntity dtoToEntity(PostDto postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        PostEntity postEntity = new PostEntity();

        postEntity.setId( postDTO.getPostId() );
        postEntity.setModifiedBy( postDTO.getModifiedBy() );
        postEntity.setCreatedDate( postDTO.getCreatedDate() );
        postEntity.setModifiedDate( postDTO.getModifiedDate() );
        postEntity.setAuthorId( userDtoToUserEntity( postDTO.getAuthorId() ) );
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
        postEntity.setDeleted( postDTO.isDeleted() );
        postEntity.setRotation( postDTO.getRotation() );

        return postEntity;
    }

    @Override
    public PostEntity updatePost(PostEntity oldPost, PostEntity newPost) {
        if ( newPost == null ) {
            return null;
        }

        oldPost.setCreatedBy( newPost.getCreatedBy() );
        oldPost.setModifiedBy( newPost.getModifiedBy() );
        oldPost.setCreatedDate( newPost.getCreatedDate() );
        oldPost.setModifiedDate( newPost.getModifiedDate() );
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
        oldPost.setRotation( newPost.getRotation() );
        oldPost.setRoomId( newPost.getRoomId() );
        oldPost.setPostType( newPost.getPostType() );

        return oldPost;
    }

    protected UserDto userEntityToUserDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUserName( userEntity.getUserName() );
        userDto.setPassword( userEntity.getPassword() );
        userDto.setEmail( userEntity.getEmail() );
        userDto.setImages( userEntity.getImages() );
        userDto.setPhoneNumber( userEntity.getPhoneNumber() );
        userDto.setPhoneNumberConfirmed( userEntity.getPhoneNumberConfirmed() );
        userDto.setTwoFactorEnable( userEntity.getTwoFactorEnable() );
        userDto.setActive( userEntity.isActive() );
        userDto.setDeleted( userEntity.isDeleted() );
        userDto.setDateOfBirth( userEntity.getDateOfBirth() );
        userDto.setAddress( userEntity.getAddress() );

        return userDto;
    }

    protected UserEntity userDtoToUserEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setImages( userDto.getImages() );
        userEntity.setUserName( userDto.getUserName() );
        userEntity.setEmail( userDto.getEmail() );
        userEntity.setPhoneNumber( userDto.getPhoneNumber() );
        userEntity.setPhoneNumberConfirmed( userDto.isPhoneNumberConfirmed() );
        userEntity.setTwoFactorEnable( userDto.isTwoFactorEnable() );
        userEntity.setActive( userDto.isActive() );
        userEntity.setDeleted( userDto.isDeleted() );
        userEntity.setDateOfBirth( userDto.getDateOfBirth() );
        userEntity.setAddress( userDto.getAddress() );

        return userEntity;
    }
}
