package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.config.TwilioConfig;
import com.roomster.roomsterbackend.dto.OtpRequestDto;
import com.roomster.roomsterbackend.dto.OtpResponseDto;
import com.roomster.roomsterbackend.dto.OtpStatus;
import com.roomster.roomsterbackend.dto.OtpValidationRequestDto;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
public class TwilioOTPService {
    @Autowired
    private TwilioConfig twilioConfig;

    private final Map<String, String> otpMap = new HashMap<>();


    public OtpResponseDto sendSMS(OtpRequestDto otpRequest) {
        OtpResponseDto otpResponseDto = null;
        try {
            PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());//to
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber()); // from
            String otp = generateOTP();
            String otpMessage = "Dear Customer , Your OTP is  " + otp + " for sending sms through Spring boot application. Thank You.";
            Message message = Message
                    .creator(to, from,
                            otpMessage)
                    .create();
            otpMap.put(otpRequest.getUserName(), otp);
            otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);
        } catch (Exception e) {
            e.printStackTrace();
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return otpResponseDto;
    }

    public String validateOtp(OtpValidationRequestDto otpValidationRequest) {
        Set<String> keys = otpMap.keySet();
        String userName = null;
        for(String key : keys)
            userName = key;
        if (otpValidationRequest.getUserName().equals(userName)) {
            otpMap.remove(userName,otpValidationRequest.getOtpNumber());
            return "OTP is valid!";
        } else {
            return "OTP is invalid!";
        }
    }

    //    generation OTP
    private String generateOTP(){
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}
