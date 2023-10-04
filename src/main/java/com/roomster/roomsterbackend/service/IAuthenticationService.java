package com.roomster.roomsterbackend.service;

import com.roomster.roomsterbackend.dto.AuthenticationResponse;
import com.roomster.roomsterbackend.dto.LoginRequest;
import com.roomster.roomsterbackend.dto.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface IAuthenticationService {
     AuthenticationResponse register(RegisterRequest request);
     AuthenticationResponse login(LoginRequest request);

}
