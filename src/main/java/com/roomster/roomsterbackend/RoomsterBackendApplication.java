package com.roomster.roomsterbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@EnableConfigurationProperties
@SpringBootApplication()
public class RoomsterBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomsterBackendApplication.class, args);
	}

}
