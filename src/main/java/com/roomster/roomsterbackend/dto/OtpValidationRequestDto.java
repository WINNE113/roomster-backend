package com.roomster.roomsterbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpValidationRequestDto {
    private String userName;
    private String otpNumber;
}
