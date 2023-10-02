package com.roomster.roomsterbackend.service;

import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepo;


    public void updateResetPasswordToken(String token, String email) throws CustomerNotFoundException {
        UserEntity user = userRepo.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepo.save(user);
        } else {
            throw new CustomerNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public UserEntity getByResetPasswordToken(String token) {
        return userRepo.findByResetPasswordToken(token);
    }

    public void updatePassword(UserEntity user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPasswordHash(encodedPassword);

        user.setResetPasswordToken(null);
        userRepo.save(user);
    }
}
