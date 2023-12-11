package com.roomster.roomsterbackend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String message;
    private String role;
    private String userName;

    public static AuthenticationResponse error(String errorMessage) {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setMessage(errorMessage);
        return response;
    }
}
