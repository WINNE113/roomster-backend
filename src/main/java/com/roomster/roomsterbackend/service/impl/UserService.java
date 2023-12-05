package com.roomster.roomsterbackend.service.impl;

import com.cloudinary.Cloudinary;
import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.auth.ChangePasswordRequest;
import com.roomster.roomsterbackend.dto.auth.OtpValidationRequestDto;
import com.roomster.roomsterbackend.dto.user.UpdateProfileRequest;
import com.roomster.roomsterbackend.dto.user.UserDto;
import com.roomster.roomsterbackend.entity.RoleEntity;
import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.mapper.UserMapper;
import com.roomster.roomsterbackend.repository.RoleRepository;
import com.roomster.roomsterbackend.repository.UserRepository;
import com.roomster.roomsterbackend.service.IService.IUserService;
import com.roomster.roomsterbackend.util.message.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private TwilioOTPService twilioOTPService;

    @Autowired
    private RoleRepository roleRepository;
    //
    private UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final Cloudinary cloudinary;


    @Autowired
    public UserService(@Lazy UserMapper userMapper, Cloudinary cloudinary) {
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
        var userEntity = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return userMapper.entityToDto(userEntity);
    }

    @Override
    public BaseResponse updateProfile(UpdateProfileRequest profileRequest, MultipartFile images, Principal connectedUser) throws IOException {

        var userEntity = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        userEntity.setUserName(profileRequest.getUserName());
        userEntity.setEmail(profileRequest.getEmail());
        userEntity.setDateOfBirth(profileRequest.getDateOfBirth());
        userEntity.setAddress(profileRequest.getAddress());
        if (images != null) {
            userEntity.setImages(getFileUrls(images));
        }
        userRepository.save(userEntity);
        return BaseResponse.success("Cập nhật thông tin thành công!");
    }

    @Override
    public BaseResponse changePassword(ChangePasswordRequest changePasswordRequest, Principal connectedUser) {
        var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            return BaseResponse.error("Password is invalid!");
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmationPassword())) {
            return BaseResponse.error("Confirmation password is invalid!");
        }
        user.setPasswordHash(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return BaseResponse.success("Update password successfully");
    }

    @Override
    public UserDto getUserById(Long userId) {
        Optional<UserEntity> userEntity = userRepository.getUserEntityByUserId(userId).filter(user -> !user.isDeleted());
        return userEntity.map(user -> userMapper.entityToDto(user)).orElse(null);
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public ResponseEntity<?> upRoleToManage(OtpValidationRequestDto otpValidationRequestDto, Principal connectedUser) {
        ResponseEntity<?> responseEntity = null;
        try {
            boolean isCorrectOTPCode = twilioOTPService.validateOtp(otpValidationRequestDto);
            if (isCorrectOTPCode) {
                var user = (UserEntity)((UsernamePasswordAuthenticationToken)connectedUser).getPrincipal();
                RoleEntity role = roleRepository.findByName("ROLE_MANAGE");
                if(role != null) {
                   // user.setRoles(Set.of(role));
                    user.getRoles().add(role);
                    userRepository.save(user);
                    return new ResponseEntity<>(BaseResponse.success("Up to role is manage is successfully!"), HttpStatus.OK);
                }
                return new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ROLE_NOT_FOUND), HttpStatus.NOT_FOUND);
            }
            responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_OTP_CODE_INCORRECT), HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    private String getFileUrls(MultipartFile multipartFile) throws IOException {
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }
}