package com.roomster.roomsterbackend.service.impl;

import com.cloudinary.Cloudinary;
import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.ChangePasswordRequest;
import com.roomster.roomsterbackend.dto.UpdateProfileRequest;
import com.roomster.roomsterbackend.dto.UserDto;
import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.mapper.UserMapper;
import com.roomster.roomsterbackend.repository.UserRepository;
import com.roomster.roomsterbackend.service.IService.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
//
    private UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final Cloudinary cloudinary;


    @Autowired
    public UserService(@Lazy UserMapper userMapper, Cloudinary cloudinary){
        this.userMapper = userMapper;
        this.cloudinary = cloudinary;
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
    public UserDto viewProfile(Principal connectedUser) {
        var userEntity = (UserEntity)((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return userMapper.entityToDto(userEntity);
    }

    @Override
    public BaseResponse updateProfile(UpdateProfileRequest profileRequest, MultipartFile images, Principal connectedUser) throws IOException {

        var userEntity = (UserEntity)((UsernamePasswordAuthenticationToken)connectedUser).getPrincipal();
        userEntity.setUserName(profileRequest.getUserName());
        userEntity.setEmail(profileRequest.getEmail());
        userEntity.setDateOfBirth(profileRequest.getDateOfBirth());
        if(!images.isEmpty()){
            userEntity.setImages(getFileUrls(images));
        }
        userRepository.save(userEntity);
        return BaseResponse.success("Update profile successfully!");
    }

//    @Override
//    public BaseResponse updateProfile(UpdateProfileRequest profileRequest, MultipartFile images) throws IOException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication != null){
//            String phoneNumber = authentication.getName();
//            Optional<UserEntity> userEntity = userRepository.findByPhoneNumber(phoneNumber);
//            if(userEntity.isPresent()){
//                userEntity.get().setUserName(profileRequest.getUserName());
//                userEntity.get().setEmail(profileRequest.getEmail());
//                userEntity.get().setDateOfBirth(profileRequest.getDateOfBirth());
//                if(!images.isEmpty()){
//                    userEntity.get().setImages(getFileUrls(images));
//                }
//                userRepository.save(userEntity.get());
//                return BaseResponse.success("Update profile successfully!");
//            }
//        }
//        return BaseResponse.error("Update profile failed!");
//
//    }

    @Override
    public BaseResponse changePassword(ChangePasswordRequest changePasswordRequest, Principal connectedUser) {
        var user = (UserEntity)((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if(!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())){
            return BaseResponse.error("Password is invalid!");
        }
        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmationPassword())){
            return BaseResponse.error("Confirmation password is invalid!");
        }
        user.setPasswordHash(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return BaseResponse.success("Update password successfully");
    }

    private String getFileUrls(MultipartFile multipartFile) throws IOException {
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }
}