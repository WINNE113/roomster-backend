package com.roomster.roomsterbackend.service;

import com.roomster.roomsterbackend.entity.UserEntity;

import java.util.Optional;

public interface IUserService {
    Optional<UserEntity> findByEmail(String email);
}
