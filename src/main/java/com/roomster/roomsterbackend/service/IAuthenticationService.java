package com.roomster.roomsterbackend.service;

import com.roomster.roomsterbackend.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface IAuthenticationService {
     public BaseResponse register(RegisterRequest request);
     BaseResponse registerTwoFactor(OtpValidationRequestDto request);
     AuthenticationResponse login(LoginRequest request);

}
