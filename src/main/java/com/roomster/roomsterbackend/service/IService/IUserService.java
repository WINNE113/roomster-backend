package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.UserDto;
import com.roomster.roomsterbackend.entity.UserEntity;

import java.util.Optional;

public interface IUserService {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

     UserDto viewProfile();

     BaseResponse updatePassword(String oldPassword, String newPassword, String confirmPassword);
}