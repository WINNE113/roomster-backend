package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.UserDto;
import com.roomster.roomsterbackend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-14T01:51:55+0700",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
*/
@Component
@Qualifier("delegate")
public class UserMapperImpl_ implements UserMapper {

    @Override
    public UserDto entityToDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setImages( userEntity.getImages() );
        userDto.setUserId( userEntity.getId() );
        userDto.setUserName( userEntity.getUserName() );
        userDto.setEmail( userEntity.getEmail() );
        userDto.setPhoneNumber( userEntity.getPhoneNumber() );
        userDto.setTwoFactorEnable( userEntity.getTwoFactorEnable() );
        userDto.setActive( userEntity.isActive() );
        userDto.setDeleted( userEntity.isDeleted() );
        userDto.setDateOfBirth( userEntity.getDateOfBirth() );
        userDto.setAddress( userEntity.getAddress() );

        return userDto;
    }

    @Override
    public UserEntity dtoToEntity(UserDto userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setImages( userDTO.getImages() );
        userEntity.setUserName( userDTO.getUserName() );
        userEntity.setEmail( userDTO.getEmail() );
        userEntity.setPhoneNumber( userDTO.getPhoneNumber() );
        userEntity.setTwoFactorEnable( userDTO.isTwoFactorEnable() );
        userEntity.setActive( userDTO.isActive() );
        userEntity.setDeleted( userDTO.isDeleted() );
        userEntity.setDateOfBirth( userDTO.getDateOfBirth() );
        userEntity.setAddress( userDTO.getAddress() );

        return userEntity;
    }
}
