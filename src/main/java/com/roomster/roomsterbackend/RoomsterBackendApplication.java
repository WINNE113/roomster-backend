package com.roomster.roomsterbackend;

import com.roomster.roomsterbackend.config.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
@SpringBootApplication()
public class RoomsterBackendApplication {



    @Autowired
    private TwilioConfig twilioConfig;

    @PostConstruct
    public void setup() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }


    public static void main(String[] args) {
        SpringApplication.run(RoomsterBackendApplication.class, args);
    }

}
