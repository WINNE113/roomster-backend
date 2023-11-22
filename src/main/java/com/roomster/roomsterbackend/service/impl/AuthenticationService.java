package com.roomster.roomsterbackend.service.impl;


import com.roomster.roomsterbackend.common.ModelCommon;
import com.roomster.roomsterbackend.common.Status;
import com.roomster.roomsterbackend.dto.*;
import com.roomster.roomsterbackend.dto.auth.*;
import com.roomster.roomsterbackend.entity.RoleEntity;
import com.roomster.roomsterbackend.entity.TokenEntity;
import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.repository.RoleRepository;
import com.roomster.roomsterbackend.repository.TokenRepository;
import com.roomster.roomsterbackend.repository.UserRepository;
import com.roomster.roomsterbackend.service.IService.IAuthenticationService;
import com.roomster.roomsterbackend.util.validator.PhoneNumberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TwilioOTPService twilioOTPService;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final Map<String, RegisterRequest> registerAccounts = new HashMap<>();

    public BaseResponse register(RegisterRequest request) {
        if (!PhoneNumberValidator.isValidPhoneNumber(request.getPhoneNumber())) {
            return BaseResponse.error("Invalid phone number format");
        }

        Optional<UserEntity> existingUser = userRepository.findByPhoneNumber(PhoneNumberValidator.normalizePhoneNumber(request.getPhoneNumber()));

        if (existingUser.isPresent()) {
            return BaseResponse.error("User with this phone number already exists");
        }
        // Xóa tài khoản hiện tại nếu số điện thoại đã tồn tại
        if (registerAccounts.containsKey(request.getPhoneNumber())) {
            registerAccounts.remove(request.getPhoneNumber());
        }

        if(request.getRole().equals(ModelCommon.USER)){
          boolean checkRegister = this.baseRegister(request);
          if(checkRegister){
              return BaseResponse.success("Successful to register");
          }
        }else if(request.getRole().equals(ModelCommon.MANAGEMENT) || request.getRole().equals(ModelCommon.ADMIN)){
            OtpRequestDto otpRequestDto = createOtpRequest(request);
            ResponseDto otpResponseDto = twilioOTPService.sendSMS(otpRequestDto);
            if (otpResponseDto.getStatus().equals(Status.DELIVERED)) {
                registerAccounts.put(request.getPhoneNumber(), request);
                return BaseResponse.success("Successful to send OTP");
            } else {
                return BaseResponse.error("Failed to send OTP");
            }
        }
        return BaseResponse.error("Failed to register! ");
    }

    private OtpRequestDto createOtpRequest(RegisterRequest request) {
        OtpRequestDto otpRequestDto = new OtpRequestDto();
        otpRequestDto.setPhoneNumber(request.getPhoneNumber());
        otpRequestDto.setUserName(request.getUserName());
        return otpRequestDto;
    }


    private boolean baseRegister(RegisterRequest request){
        RoleEntity role = roleRepository.findByName(request.getRole());
        if(role != null){
            UserEntity user = new UserEntity();
            user.setUserName(request.getUserName());
            user.setPhoneNumber(PhoneNumberValidator.normalizePhoneNumber(request.getPhoneNumber()));
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            user.setCreatedBy(0L);
            user.setRoles(Set.of(role));
            if(request.getRole().equals(ModelCommon.USER)){
                user.setPhoneNumberConfirmed(false);
                user.setTwoFactorEnable(false);
            }else if(request.getRole().equals(ModelCommon.ADMIN) || request.getRole().equals(ModelCommon.MANAGEMENT)){
                user.setPhoneNumberConfirmed(true);
                user.setTwoFactorEnable(true);
            }
            user.setActive(true);
            user.setDeleted(false);
            userRepository.save(user);
            return true;
        }else{
            return false;
        }
    }

    public BaseResponse registerTwoFactor(OtpValidationRequestDto requestDto) {
            boolean checkValidOTP = twilioOTPService.validateOtp(requestDto);
            if (checkValidOTP) {
                for (Map.Entry<String, RegisterRequest> entry : registerAccounts.entrySet()
                ) {
                    RegisterRequest item = entry.getValue();
                    if (item.getUserName().equals(requestDto.getUserName())) {
                        RegisterRequest request = new RegisterRequest();
                        request.setUserName(item.getUserName());
                        request.setPhoneNumber(item.getPhoneNumber());
                        request.setPassword(item.getPassword());
                        request.setRole(item.getRole());
                        boolean checkRegister =  this.baseRegister(request);
                        if(!checkRegister){
                            return BaseResponse.error("Register is fail!");
                        }
                    }
                }
                return BaseResponse.success("OTP is valid");
            }
        return BaseResponse.error("OTP is invalid");
    }

    public AuthenticationResponse login(LoginRequest request) {
        // normalize phone number
        String normalizePhoneNumber = PhoneNumberValidator.normalizePhoneNumber(request.getPhoneNumber());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            normalizePhoneNumber,
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            return AuthenticationResponse.error("Authentication failed. Invalid phone number or password.");
        }

       var user = userRepository.findByPhoneNumber(normalizePhoneNumber);
        if (user.isPresent()) {
            var jwtToken = jwtService.generateToken(user.get());

            revokeAllUserTokens(user.get());
            saveUserToken(user, jwtToken);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .message("Get token successfully!")
                    .build();
        }else{
            return AuthenticationResponse.error("User Not Found!");
        }
    }

    private void saveUserToken(Optional<UserEntity> user, String jwtToken) {
        var token = TokenEntity.builder()
                .userToken(user.get())
                .token(jwtToken)
                .tokeType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity user){
         var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
         if(validUserTokens.isEmpty())
             return;
         validUserTokens.forEach( t -> {
             t.setRevoked(true);
             t.setExpired(true);
         });
         tokenRepository.saveAll(validUserTokens);
    }
}
