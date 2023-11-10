package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.UserDto;
import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.mapper.UserMapper;
import com.roomster.roomsterbackend.repository.UserRepository;
import com.roomster.roomsterbackend.service.IService.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    private UserMapper userMapper;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    public UserService(@Lazy UserMapper userMapper){
        this.userMapper = userMapper;
    }
    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<UserEntity> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public UserDto viewProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            String phoneNumber = authentication.getName();
            System.out.println("Image is: " + userRepository.findByPhoneNumber(phoneNumber).get()/*.getImages()*/);
            return userMapper.entityToDto(userRepository.findByPhoneNumber(phoneNumber).get());
        }
        return null;
    }

    @Override
    public BaseResponse updatePassword(String oldPassword, String newPassword, String confirmPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            String phoneNumber = authentication.getName();
            Optional<UserEntity> userEntity =  userRepository.findByPhoneNumber(phoneNumber);
            if(userEntity.isPresent()){
                if(newPassword.equals(confirmPassword)){
                    if(passwordEncoder.matches(oldPassword, userEntity.get().getPasswordHash())){
                        userEntity.get().setPasswordHash(passwordEncoder.encode(newPassword));
                        userRepository.save(userEntity.get());
                        return BaseResponse.success("Change password successfully!");
                    }else{
                        return BaseResponse.error("Password is not valid!");
                    }
                }else{
                    return BaseResponse.error("Confirm password is not valid!");
                }
            }
        }

        return BaseResponse.error("Update password is fail!");
    }

    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

}