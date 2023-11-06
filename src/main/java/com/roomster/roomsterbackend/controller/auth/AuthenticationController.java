package com.roomster.roomsterbackend.controller.auth;

import com.roomster.roomsterbackend.dto.*;
import com.roomster.roomsterbackend.service.IService.IAuthenticationService;
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

    @PostMapping("/registration")
    public ResponseEntity<BaseResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/verification-otp")
    public ResponseEntity<BaseResponse> verificationOTP(@RequestBody OtpValidationRequestDto requestDto){
        return ResponseEntity.ok(authenticationService.registerTwoFactor(requestDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request){

        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("/process")
    public String processSMS() {
        return "SMS sent";
    }

    @PostMapping("/demo-send-otp")
    public ResponseDto sendOTP(@RequestBody OtpRequestDto requestDto) {
        log.info("inside sendOtp :: "+requestDto.getUserName());
        return twilioOTPService.sendSMS(requestDto);
    }

    @PostMapping("/demo-validate-otp")
    public boolean validateOTP(@RequestBody OtpValidationRequestDto otpValidationRequestDto) {
        log.info("inside validateOtp :: "+otpValidationRequestDto.getUserName()+" "+otpValidationRequestDto.getOtpNumber());
        return twilioOTPService.validateOtp(otpValidationRequestDto);
    }
}
