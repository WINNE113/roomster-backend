package com.roomster.roomsterbackend.service;

import java.util.Optional;

public interface IUserService {
    Optional<UserEntity> findByEmail(String email);
}
