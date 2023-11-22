package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.dto.*;
import com.roomster.roomsterbackend.dto.auth.AuthenticationResponse;
import com.roomster.roomsterbackend.dto.auth.LoginRequest;
import com.roomster.roomsterbackend.dto.auth.OtpValidationRequestDto;
import com.roomster.roomsterbackend.dto.auth.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface IAuthenticationService {
     public BaseResponse register(RegisterRequest request);
     BaseResponse registerTwoFactor(OtpValidationRequestDto request);
     AuthenticationResponse login(LoginRequest request);

}
