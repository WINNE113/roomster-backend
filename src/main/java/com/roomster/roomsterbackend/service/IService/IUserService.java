package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.entity.UserEntity;

import java.util.Optional;

public interface IUserService {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findByUserName(String userName);

}