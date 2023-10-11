package com.roomster.roomsterbackend.controller.auth;

import com.roomster.roomsterbackend.dto.*;
import com.roomster.roomsterbackend.service.IAuthenticationService;
import com.roomster.roomsterbackend.service.impl.TwilioOTPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    private final TwilioOTPService twilioOTPService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request){

        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("/process")
    public String processSMS() {
        return "SMS sent";
    }

    @PostMapping("/send-otp")
    public OtpResponseDto sendOTP(@RequestBody OtpRequestDto requestDto) {
        log.info("inside sendOtp :: "+requestDto.getUserName());
        return twilioOTPService.sendSMS(requestDto);
    }

    @PostMapping("/validate-otp")
    public String validateOTP(@RequestBody OtpValidationRequestDto otpValidationRequestDto) {
        log.info("inside validateOtp :: "+otpValidationRequestDto.getUserName()+" "+otpValidationRequestDto.getOtpNumber());
        return twilioOTPService.validateOtp(otpValidationRequestDto);
    }
}
