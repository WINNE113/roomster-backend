package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.user.UserDto;
import com.roomster.roomsterbackend.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-27T20:30:13+0700",
    comments = "version: 1.4.0.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class UserMapperImpl_ implements UserMapper {

    @Override
    public UserDto entityToDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUserId( userEntity.getId() );
        userDto.setImages( userEntity.getImages() );
        userDto.setUserName( userEntity.getUserName() );
        userDto.setEmail( userEntity.getEmail() );
        userDto.setPhoneNumber( userEntity.getPhoneNumber() );
        userDto.setPhoneNumberConfirmed( userEntity.getPhoneNumberConfirmed() );
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
        userEntity.setPhoneNumberConfirmed( userDTO.isPhoneNumberConfirmed() );
        userEntity.setTwoFactorEnable( userDTO.isTwoFactorEnable() );
        userEntity.setActive( userDTO.isActive() );
        userEntity.setDeleted( userDTO.isDeleted() );
        userEntity.setDateOfBirth( userDTO.getDateOfBirth() );
        userEntity.setAddress( userDTO.getAddress() );

        return userEntity;
    }
}
