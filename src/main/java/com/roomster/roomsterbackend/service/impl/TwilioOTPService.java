package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.config.TwilioConfig;
import com.roomster.roomsterbackend.dto.OtpRequestDto;
import com.roomster.roomsterbackend.dto.OtpValidationRequestDto;
import com.roomster.roomsterbackend.dto.ResponseDto;
import com.roomster.roomsterbackend.dto.Status;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TwilioOTPService {
    @Autowired
    private TwilioConfig twilioConfig;

    private final Map<String, String> otpMap = new HashMap<>();


    public ResponseDto sendSMS(OtpRequestDto otpRequest) {
        ResponseDto otpResponseDto = null;
        try {
            PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());//to
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber()); // from
            String otp = generateOTP();
            String otpMessage = "Dear Customer , Your OTP is  " + otp + " for sending sms through Spring boot application. Thank You.";
            Message message = Message.creator(to, from, otpMessage).create();
            otpMap.put(otpRequest.getUserName(), otp);
            otpResponseDto = new ResponseDto(Status.DELIVERED, otpMessage);
        } catch (Exception e) {
            otpResponseDto = new ResponseDto(Status.FAILED, e.getMessage());
        }
        return otpResponseDto;
    }

    public boolean validateOtp(OtpValidationRequestDto otpValidationRequest) {
        String userName = otpValidationRequest.getUserName();
        String storedOtp = otpMap.get(userName);

        if (storedOtp != null && storedOtp.equals(otpValidationRequest.getOtpNumber())) {
            // OTP is valid
            otpMap.remove(userName);
            return true;
        } else {
            // OTP is invalid
            return false;
        }
    }

    //    generation OTP
    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}
