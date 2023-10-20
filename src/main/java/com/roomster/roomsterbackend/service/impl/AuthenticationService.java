package com.roomster.roomsterbackend.service.impl;


import com.roomster.roomsterbackend.dto.*;
import com.roomster.roomsterbackend.entity.RoleEntity;
import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.repository.UserRepository;
import com.roomster.roomsterbackend.service.IAuthenticationService;
import com.roomster.roomsterbackend.util.validator.PhoneNumberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TwilioOTPService twilioOTPService;
    private final Map<String, RegisterRequest> registerAccounts = new HashMap<>();

    public BaseResponse register(RegisterRequest request) {

        Optional<UserEntity> existingUser = repository.findByPhoneNumber(PhoneNumberValidator.normalizePhoneNumber(request.getPhoneNumber()));

        if (!PhoneNumberValidator.isValidPhoneNumber(request.getPhoneNumber())) {
            return BaseResponse.error("Invalid phone number format");
        }
        if (existingUser.isPresent()) {
            return BaseResponse.error("User with this phone number already exists");
        }
        // Xóa tài khoản hiện tại nếu số điện thoại đã tồn tại
        if (registerAccounts.containsKey(request.getPhoneNumber())) {
            registerAccounts.remove(request.getPhoneNumber());
        }

        OtpRequestDto otpRequestDto = createOtpRequest(request);
        ResponseDto otpResponseDto = twilioOTPService.sendSMS(otpRequestDto);
        if (otpResponseDto.getStatus().equals(Status.DELIVERED)) {
            registerAccounts.put(request.getPhoneNumber(), request);
            return BaseResponse.success("Successful to send OTP");
        } else {
            return BaseResponse.error("Failed to send OTP");
        }
    }

    private OtpRequestDto createOtpRequest(RegisterRequest request) {
        OtpRequestDto otpRequestDto = new OtpRequestDto();
        otpRequestDto.setPhoneNumber(request.getPhoneNumber());
        otpRequestDto.setUserName(request.getUserName());
        return otpRequestDto;
    }

    public BaseResponse registerTwoFactor(OtpValidationRequestDto requestDto) {

        boolean checkValidOTP = twilioOTPService.validateOtp(requestDto);
        if (checkValidOTP) {
            for (Map.Entry<String, RegisterRequest> entry : registerAccounts.entrySet()
            ) {
                String phoneNumber = entry.getKey();
                RegisterRequest item = entry.getValue();
                if (item.getUserName().equals(requestDto.getUserName())) {
                    UserEntity user = new UserEntity();
                    user.setUserName(item.getUserName());
                    user.setPhoneNumber(PhoneNumberValidator.normalizePhoneNumber(item.getPhoneNumber()));
                    user.setPasswordHash(passwordEncoder.encode(item.getPassword()));
                    user.setRole(item.getRole());
                    repository.save(user);
                    registerAccounts.remove(phoneNumber);
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

       var user = repository.findByPhoneNumber(normalizePhoneNumber);
        if (user.isPresent()) {
            var jwtToken = jwtService.generateToken(user.get());
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .message("Get token successfully!")
                    .build();
        }else{
            return AuthenticationResponse.error("User Not Found!");
        }
    }


}
