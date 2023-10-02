package com.roomster.roomsterbackend.service.impl;
/*
import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.repository.UserRepository;
import com.roomster.roomsterbackend.service.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional findUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Override
    public void save(UserEntity user) {
        userRepository.save(user);
    }

    public void updatePassword(UserEntity user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPasswordHash(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
*/