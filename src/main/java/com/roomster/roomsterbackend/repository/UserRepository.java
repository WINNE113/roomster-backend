package com.roomster.roomsterbackend.repository;
/*
import com.roomster.roomsterbackend.entity.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByResetToken(String resetToken);

}
*/

import com.roomster.roomsterbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findByEmail(String email);

    public UserEntity findByResetPasswordToken(String token);
}

