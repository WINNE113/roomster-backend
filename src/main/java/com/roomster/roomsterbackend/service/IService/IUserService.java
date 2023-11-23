package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.auth.ChangePasswordRequest;
import com.roomster.roomsterbackend.dto.user.UpdateProfileRequest;
import com.roomster.roomsterbackend.dto.user.UserDto;
import com.roomster.roomsterbackend.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findByUserName(String userName);

    UserDto viewProfile(Principal connectedUser);

    BaseResponse updateProfile(UpdateProfileRequest profileRequest, MultipartFile images, Principal connectedUser) throws IOException;

    BaseResponse changePassword(ChangePasswordRequest changePasswordRequest, Principal connectedUser);

    UserDto getUserById(Long userId);

    List<UserEntity> getAllUser();
}