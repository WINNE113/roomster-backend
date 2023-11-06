package com.roomster.roomsterbackend.controller.auth;

import com.roomster.roomsterbackend.dto.*;
import com.roomster.roomsterbackend.service.IService.IAuthenticationService;
import com.roomster.roomsterbackend.service.impl.TwilioOTPService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            description = "Must register by only phoneNumber: +84332101032 and Format role is [ROLE_USER, ROLE_MANAGE]",
            summary = "Endpoint For Register"
    )
    @PostMapping("/registration")
    public ResponseEntity<BaseResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(
            description = "Get OTP code from smart phone",
            summary = "Endpoint For Verification OTP code"
    )
    @PostMapping("/verification-otp")
    public ResponseEntity<BaseResponse> verificationOTP(@RequestBody OtpValidationRequestDto requestDto){
        return ResponseEntity.ok(authenticationService.registerTwoFactor(requestDto));
    }

    @Operation(
            description = "Phone Number Must have pre: +84 ",
            summary = "Endpoint For Login"
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request){

        return ResponseEntity.ok(authenticationService.login(request));
    }
    @Hidden
    @GetMapping("/process")
    public String processSMS() {
        return "SMS sent";
    }

    @Hidden
    @PostMapping("/demo-send-otp")
    public ResponseDto sendOTP(@RequestBody OtpRequestDto requestDto) {
        log.info("inside sendOtp :: "+requestDto.getUserName());
        return twilioOTPService.sendSMS(requestDto);
    }

    @Hidden
    @PostMapping("/demo-validate-otp")
    public boolean validateOTP(@RequestBody OtpValidationRequestDto otpValidationRequestDto) {
        log.info("inside validateOtp :: "+otpValidationRequestDto.getUserName()+" "+otpValidationRequestDto.getOtpNumber());
        return twilioOTPService.validateOtp(otpValidationRequestDto);
    }
}
